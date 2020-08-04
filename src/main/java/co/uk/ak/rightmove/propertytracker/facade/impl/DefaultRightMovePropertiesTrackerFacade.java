package co.uk.ak.rightmove.propertytracker.facade.impl;

import co.uk.ak.rightmove.propertytracker.client.RightMoveWebClient;
import co.uk.ak.rightmove.propertytracker.configuration.EmailSender;
import co.uk.ak.rightmove.propertytracker.dto.LettingPropertiesTrackingResult;
import co.uk.ak.rightmove.propertytracker.dto.RightMoveResult;
import co.uk.ak.rightmove.propertytracker.facade.RightMovePropertiesTrackerFacade;
import co.uk.ak.rightmove.propertytracker.mapper.TrackingResultMapper;
import co.uk.ak.rightmove.propertytracker.model.TrackingResultModel;
import co.uk.ak.rightmove.propertytracker.repository.TrackingResultRepository;
import co.uk.ak.rightmove.propertytracker.service.RightMoveTrackingService;
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
   private final EmailSender emailSender;

   @Override
   public void trackProperties(String locationId)
   {
      final RightMoveResult rightMoveResult = webClient.callRightMove(locationId);
      LOG.info("Found [{}] results for locationId [{}] ", rightMoveResult.getResultCount(), locationId);
      final LettingPropertiesTrackingResult trackingResult = trackingService.trackProperties(rightMoveResult);
      LOG.info("Successfully tracked properties: Summary numberOfPropertiesLet : [{}]", trackingResult.getNumberOfPropertiesLet());
      final TrackingResultModel trackingResultModel = trackingResultMapper.trackingResultToModel(trackingResult);
      trackingResultRepository.save(trackingResultModel);
      LOG.info("Successfully converted and saved tracking result in DB");
      trackingService.refreshProperties(rightMoveResult);
      LOG.info("Successfully converted and saved RightMove properties in DB");
      if (trackingResult.needsReporting())
      {
         emailSender.sendEmail(trackingResult);
         LOG.info("Email sent...!");
      }
      else
      {
         LOG.info("Nothing to report.");
      }

   }
}
