package co.uk.ak.propertytracker.facade.impl;

import co.uk.ak.propertytracker.dto.LettingPropertiesTrackingResult;
import co.uk.ak.propertytracker.dto.PropertyDto;
import co.uk.ak.propertytracker.emails.EmailService;
import co.uk.ak.propertytracker.endpoints.searchcriteriadto.SearchCriteriaDto;
import co.uk.ak.propertytracker.facade.PropertiesTrackerFacade;
import co.uk.ak.propertytracker.mapper.PropertyDtoToPropertyModelMapper;
import co.uk.ak.propertytracker.mapper.RightMovePropertyToPropertyDtoMapper;
import co.uk.ak.propertytracker.mapper.TrackingResultMapper;
import co.uk.ak.propertytracker.model.PropertyModel;
import co.uk.ak.propertytracker.model.PropertyUpdateRecordModel;
import co.uk.ak.propertytracker.model.TrackingResultModel;
import co.uk.ak.propertytracker.repository.PropertyRepository;
import co.uk.ak.propertytracker.repository.PropertyUpdateRecordRepository;
import co.uk.ak.propertytracker.repository.TrackingResultRepository;
import co.uk.ak.propertytracker.rightmove.client.RightMoveWebClient;
import co.uk.ak.propertytracker.rightmove.dto.RightMoveResult;
import co.uk.ak.propertytracker.service.PropertyDao;
import co.uk.ak.propertytracker.service.RightMoveTrackingService;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DefaultPropertiesTrackerFacade implements PropertiesTrackerFacade
{
   private static final Logger LOG = LoggerFactory.getLogger(DefaultPropertiesTrackerFacade.class);

   private final RightMoveWebClient webClient;
   private final RightMoveTrackingService trackingService;
   private final TrackingResultRepository trackingResultRepository;
   private final TrackingResultMapper trackingResultMapper;
   private final EmailService emailSender;

   private final RightMovePropertyToPropertyDtoMapper rightMovePropertyToPropertyDtoMapper;
   private final PropertyDao propertyDao;
   private final PropertyUpdateRecordRepository propertyUpdateRecordRepository;
   private final PropertyDtoToPropertyModelMapper propertyDtoToPropertyModelMapper;
   private final PropertyRepository propertyRepository;

   @Override
   public void trackPropertiesV2(final SearchCriteriaDto searchCriteria)
   {
      final Date reportStartTime = DateTime.now().toDate();
      //Query RightMove
      final RightMoveResult rightMoveResult = webClient.callRightMove(searchCriteria);

      //Save/update in DB
      rightMoveResult.getProperties().forEach(rightMoveProperty -> {
         final PropertyDto propertyDto = rightMovePropertyToPropertyDtoMapper.rightMovePropertyToPropertyDto(rightMoveProperty);
         LOG.info("Property DTO found for id [{}] ", propertyDto.getId());
         propertyDao.createOrUpdate(propertyDto);
      });

      //Generate Reports
      final AtomicInteger numberOfLetProperties = new AtomicInteger();
      final List<PropertyDto> letProperties = new ArrayList<>();

      final List<PropertyUpdateRecordModel> propertyUpdates = propertyUpdateRecordRepository.findByCreationTimeGreaterThan(reportStartTime);
      LOG.info("update records found [{}]", propertyUpdates.size());
      propertyUpdates.stream()
               .filter(p -> p.getField().equalsIgnoreCase("displayStatus"))
               .forEach(p -> {
                  numberOfLetProperties.incrementAndGet();
                  letProperties.add(propertyDtoToPropertyModelMapper.propertyModelPropertyDtoMapper(p.getProperty()));
               });

      final List<PropertyModel> newPropertiesOnTheMarket = propertyRepository.findByCreationTimeGreaterThan(reportStartTime);
      LOG.info("Number of new properties found [{}]", newPropertiesOnTheMarket.size());
      final List<PropertyDto> newPropertiesDtoOnTheMarket = newPropertiesOnTheMarket.stream()
               .map(propertyDtoToPropertyModelMapper::propertyModelPropertyDtoMapper)
               .collect(Collectors.toList());

      //Send Emails
      final LettingPropertiesTrackingResult trackingResult = LettingPropertiesTrackingResult.builder()
               .numberOfLetProperties(numberOfLetProperties.get())
               .letProperties(letProperties)
               .numberOfNewPropertiesOnMarket(newPropertiesDtoOnTheMarket.size())
               .newPropertiesOnMarket(newPropertiesDtoOnTheMarket)
               .build();

      if (trackingResult.needsReporting())
      {
         emailSender.sendLettingReportsEmail(trackingResult);
         LOG.info("Email sent...!");
      }
      else
      {
         LOG.info("Nothing to report.");
      }

   }

   @Override
   public LettingPropertiesTrackingResult trackProperties(final SearchCriteriaDto searchCriteria)
   {
      final RightMoveResult rightMoveResult = webClient.callRightMove(searchCriteria);
      LOG.info("Found [{}] results for locationId [{}] ", rightMoveResult.getResultCount(), searchCriteria.getLocationIdentifier());
      final LettingPropertiesTrackingResult trackingResult = trackingService.trackProperties(rightMoveResult);
      LOG.info("Successfully tracked properties: Summary numberOfPropertiesLet : [{}]", trackingResult.getNumberOfLetProperties());
      trackingResult.getLetProperties().forEach(property -> {
         LOG.info("The property let [{}}", property.getId());
      });

      final TrackingResultModel trackingResultModel = trackingResultMapper.trackingResultToModel(trackingResult);
      trackingResultRepository.save(trackingResultModel);
      LOG.info("Successfully converted and saved tracking result in DB");
      trackingService.refreshProperties(rightMoveResult);
      LOG.info("Successfully converted and saved RightMove properties in DB");
      if (trackingResult.needsReporting())
      {
         emailSender.sendLettingReportsEmail(trackingResult);
         LOG.info("Email sent...!");
      }
      else
      {
         LOG.info("Nothing to report.");
      }
      return trackingResult;
   }
}
