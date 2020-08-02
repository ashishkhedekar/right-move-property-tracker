package co.uk.ak.rightmove.propertytracker.scheduler;

import co.uk.ak.rightmove.propertytracker.client.RightMoveWebClient;
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

   @Scheduled(cron = "0 0 1 * * *")
   public void runRightMoveQuery()
   {
      LOG.info("Going to run right move query");
      facade.trackProperties("");
   }
}
