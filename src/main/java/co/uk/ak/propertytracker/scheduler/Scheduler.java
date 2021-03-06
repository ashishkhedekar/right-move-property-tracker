package co.uk.ak.propertytracker.scheduler;

import co.uk.ak.propertytracker.facade.PropertiesTrackerFacade;
import co.uk.ak.propertytracker.facade.SearchCriteriaFacade;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Data
@RequiredArgsConstructor
public class Scheduler
{
   private static final Logger LOG = LoggerFactory.getLogger(Scheduler.class);

   private final PropertiesTrackerFacade propertiesTrackerFacade;
   private final SearchCriteriaFacade searchCriteriaFacade;

   //Runs every house between 6am till 11pm
   @Scheduled(cron = "0 0 6-23 * * *")
   public void fetchRightMovePropertyUpdates()
   {
      LOG.info("Scheduler triggered for fetchRightMovePropertyUpdates");
      searchCriteriaFacade.getAll().forEach(propertiesTrackerFacade::trackProperties);
      LOG.info("Scheduler finished for fetchRightMovePropertyUpdates");
   }

   @Scheduled(cron = "0 30 22 * * *")
   public void findAndMarkOffMarketProperties()
   {
      LOG.info("Scheduler triggered for findAndMarkOffMarketProperties");
      propertiesTrackerFacade.findAndMarkOffMarketProperties();
      LOG.info("Scheduler finished for findAndMarkOffMarketProperties");
   }
}
