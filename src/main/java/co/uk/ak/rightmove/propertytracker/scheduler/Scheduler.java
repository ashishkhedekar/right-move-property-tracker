package co.uk.ak.rightmove.propertytracker.scheduler;

import co.uk.ak.rightmove.propertytracker.client.RightMoveWebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class Scheduler
{
   private static final Logger LOG = LoggerFactory.getLogger(Scheduler.class);

   private final RightMoveWebClient rightMoveWebClient;

   public Scheduler(RightMoveWebClient rightMoveWebClient)
   {
      this.rightMoveWebClient = rightMoveWebClient;
   }

   @Scheduled(cron = "0 0 1 * * *")
   public void runRightMoveQuery()
   {
      LOG.info("Going to run right move query");
      rightMoveWebClient.callRightMove();
   }
}
