package co.uk.ak.propertytracker.facade.impl;

import co.uk.ak.propertytracker.dto.MarketMovementReport;
import co.uk.ak.propertytracker.dto.PropertyDto;
import co.uk.ak.propertytracker.emails.EmailService;
import co.uk.ak.propertytracker.endpoints.searchcriteriadto.SearchCriteriaDto;
import co.uk.ak.propertytracker.facade.PropertiesTrackerFacade;
import co.uk.ak.propertytracker.mapper.RightMovePropertyToPropertyDtoMapper;
import co.uk.ak.propertytracker.rightmove.client.RightMoveWebClient;
import co.uk.ak.propertytracker.rightmove.dto.RightMoveResult;
import co.uk.ak.propertytracker.service.MarketMovementReportService;
import co.uk.ak.propertytracker.service.PropertyDao;
import co.uk.ak.propertytracker.service.RightMoveSearchResultDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class DefaultPropertiesTrackerFacade implements PropertiesTrackerFacade
{
   private static final Logger LOG = LoggerFactory.getLogger(DefaultPropertiesTrackerFacade.class);

   private final RightMoveWebClient webClient;
   private final RightMoveSearchResultDao rightMoveSearchResultDao;
   private final RightMovePropertyToPropertyDtoMapper rightMovePropertyToPropertyDtoMapper;
   private final PropertyDao propertyDao;
   private final MarketMovementReportService marketMovementReportService;
   private final EmailService emailSender;
   private final ObjectMapper objectMapper;

   @Override
   public void trackProperties(final SearchCriteriaDto searchCriteria)
   {
      try
      {
         final Date reportStartTime = DateTime.now().toDate();
         //Query RightMove
         final String rightMoveResponse = webClient.callRightMove(searchCriteria);

         //Save the response from rightmove for future purpose
         rightMoveSearchResultDao.save(rightMoveResponse, searchCriteria.getId());
         final RightMoveResult rightMoveResult = objectMapper.readValue(rightMoveResponse, RightMoveResult.class);
         LOG.info("Found [{}] properties from web", rightMoveResult.getProperties().size());

         //Save/update in DB
         rightMoveResult.getProperties().forEach(rightMoveProperty -> {
            final PropertyDto propertyDto = rightMovePropertyToPropertyDtoMapper.rightMovePropertyToPropertyDto(rightMoveProperty);
            LOG.info("Property DTO found for id [{}] ", propertyDto.getId());
            propertyDao.createOrUpdate(propertyDto);
         });

         //Generate Reports
         final MarketMovementReport trackingResult = marketMovementReportService.generateMarketMovementReport(reportStartTime, searchCriteria.getChannel());

         //send email
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
      catch (Exception e)
      {
         e.printStackTrace();
         emailSender.sendSomethingWentWrongEmail(e.getMessage());
      }
   }
}
