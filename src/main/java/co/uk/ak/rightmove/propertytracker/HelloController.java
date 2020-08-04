package co.uk.ak.rightmove.propertytracker;

import co.uk.ak.rightmove.propertytracker.configuration.EmailSender;
import co.uk.ak.rightmove.propertytracker.dto.LettingPropertiesTrackingResult;
import co.uk.ak.rightmove.propertytracker.facade.DummyFacade;
import co.uk.ak.rightmove.propertytracker.facade.RightMovePropertiesTrackerFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class HelloController
{
   private final RightMovePropertiesTrackerFacade trackerFacade;

   private final DummyFacade dummyFacade;
   private final EmailSender emailSender;

   @GetMapping(name = "/")
   public ResponseEntity<String> message()
   {
      trackerFacade.trackProperties("REGION%5E239");
      return ResponseEntity.status(HttpStatus.OK).body("This app is doing some important stuff");
   }

   @GetMapping(path = "/howmany")
   public ResponseEntity<String> howMany()
   {
      final long properties = dummyFacade.howManyProperties();
      final long results = dummyFacade.howManyResults();
      return ResponseEntity.status(HttpStatus.OK).body(String.format("There are currently [%s] properties and [%s] results ", properties, results));
   }

   @GetMapping(path = "/sendemail")
   public ResponseEntity<String> sendEmail()
   {

      try
      {
         emailSender.sendEmail(LettingPropertiesTrackingResult.builder().numberOfPropertiesLet(10).build());

         return ResponseEntity.status(HttpStatus.OK).body("Email was sent successfully");
      }
      catch (Exception e)
      {
         e.printStackTrace();
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong, check emails");
      }
   }
}
