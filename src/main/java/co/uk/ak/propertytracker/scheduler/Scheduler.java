package co.uk.ak.propertytracker.scheduler;

import co.uk.ak.propertytracker.facade.RightMovePropertiesTrackerFacade;
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

   private final RightMovePropertiesTrackerFacade facade;

   //Runs every house between 7am till 10pm
   @Scheduled(cron = "0 0 7-22 * * *")
   public void runRightMoveQuery()
   {
      facade.trackProperties("REGION%5E239");
   }
}