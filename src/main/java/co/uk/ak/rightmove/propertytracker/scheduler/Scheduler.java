package co.uk.ak.rightmove.propertytracker.scheduler;

import co.uk.ak.rightmove.propertytracker.facade.RightMovePropertiesTrackerFacade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Scheduler
{
   private static final Logger LOG = LoggerFactory.getLogger(Scheduler.class);

   private RightMovePropertiesTrackerFacade facade;

   //Runs every house between 7am till 10pm
   @Scheduled(cron = "0 0 7-22 * * *")
   public void runRightMoveQuery()
   {
      facade.trackProperties("REGION%5E239");
   }
}
