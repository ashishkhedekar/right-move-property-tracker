package co.uk.ak.propertytracker.facade.impl;

import co.uk.ak.propertytracker.dto.LettingPropertiesTrackingResult;
import co.uk.ak.propertytracker.dto.PropertyDto;
import co.uk.ak.propertytracker.emails.EmailService;
import co.uk.ak.propertytracker.endpoints.searchcriteriadto.SearchCriteriaDto;
import co.uk.ak.propertytracker.facade.RightMovePropertiesTrackerFacade;
import co.uk.ak.propertytracker.mapper.RightMovePropertyToPropertyDtoMapper;
import co.uk.ak.propertytracker.mapper.TrackingResultMapper;
import co.uk.ak.propertytracker.model.TrackingResultModel;
import co.uk.ak.propertytracker.repository.TrackingResultRepository;
import co.uk.ak.propertytracker.rightmove.client.RightMoveWebClient;
import co.uk.ak.propertytracker.rightmove.dto.RightMoveResult;
import co.uk.ak.propertytracker.service.PropertyDao;
import co.uk.ak.propertytracker.service.RightMoveTrackingService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DefaultRightMovePropertiesTrackerFacade implements RightMovePropertiesTrackerFacade
{
   private static final Logger LOG = LoggerFactory.getLogger(DefaultRightMovePropertiesTrackerFacade.class);

   private final RightMoveWebClient webClient;
   private final RightMoveTrackingService trackingService;
   private final TrackingResultRepository trackingResultRepository;
   private final TrackingResultMapper trackingResultMapper;
   private final EmailService emailSender;

   private final RightMovePropertyToPropertyDtoMapper rightMovePropertyToPropertyDtoMapper;
   private final PropertyDao propertyDao;

   @Override
   public void trackPropertiesV2(final SearchCriteriaDto searchCriteria)
   {
      //Query RightMove
      final RightMoveResult rightMoveResult = webClient.callRightMove(searchCriteria);

      //Save in DB
      rightMoveResult.getProperties().forEach(rightMoveProperty -> {
         final PropertyDto propertyDto = rightMovePropertyToPropertyDtoMapper.rightMovePropertyToPropertyModel(rightMoveProperty);
         LOG.info("Property DTO found for id [{}] ", propertyDto.getId());
         propertyDao.createOrUpdate(propertyDto);

      });

      //Generate Reports


      //Send Emails

   }

   @Override
   public LettingPropertiesTrackingResult trackProperties(final SearchCriteriaDto searchCriteria)
   {
      final RightMoveResult rightMoveResult = webClient.callRightMove(searchCriteria);
      LOG.info("Found [{}] results for locationId [{}] ", rightMoveResult.getResultCount(), searchCriteria.getLocationIdentifier());
      final LettingPropertiesTrackingResult trackingResult = trackingService.trackProperties(rightMoveResult);
      LOG.info("Successfully tracked properties: Summary numberOfPropertiesLet : [{}]", trackingResult.getNumberOfPropertiesLet());
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
