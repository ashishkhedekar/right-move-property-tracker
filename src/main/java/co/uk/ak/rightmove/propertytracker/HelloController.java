package co.uk.ak.rightmove.propertytracker;

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
}
