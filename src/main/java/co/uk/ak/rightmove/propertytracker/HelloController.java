package co.uk.ak.rightmove.propertytracker;

import co.uk.ak.rightmove.propertytracker.dto.LettingPropertiesTrackingResult;
import co.uk.ak.rightmove.propertytracker.dto.Property;
import co.uk.ak.rightmove.propertytracker.dto.PropertyImages;
import co.uk.ak.rightmove.propertytracker.emails.EmailService;
import co.uk.ak.rightmove.propertytracker.facade.DummyFacade;
import co.uk.ak.rightmove.propertytracker.facade.RightMovePropertiesTrackerFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class HelloController
{
   private final RightMovePropertiesTrackerFacade trackerFacade;

   private final DummyFacade dummyFacade;
   private final EmailService emailService;

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
      Property property1 = Property.builder()
               .displayStatus("Let agreed")
               .displayAddress("Buckingham")
               .summary("Student HMO Property 2020/2021 Academic Year - This 5 double bedroom property benefits from two bathrooms, a large fully fitted kitchen , common room , patio garden and driveway parking. The property has the Buckingham University campus on its doorstep and the town centre within a minute...")
               .propertySubType("Terraced")
               .bedrooms(5)
               .propertyImages(PropertyImages.builder().mainMapImageSrc("https://media.rightmove.co.uk:443/dir/crop/10:9-16:9/70k/69152/91967702/69152_2548866_IMG_01_0001_max_296x197.jpg").build())
               .fullPropertyUrl("https://www.rightmove.co.uk/property-to-rent/property-91967702.html")
               .daysOnMarket(10)
               .build();

      Property property2 = Property.builder()
//               .displayStatus("Let agreed")
               .displayAddress("24 Needlepin Way, Buckingham")
               .summary("Well presented four bedroom DETACHED house situated on the popular Lace Hill development. The property benefits from being IN CATCHMENT FOR LOCAL SCHOOLS and WALKING DISTANCE TO THE TOWN CENTRE. The property briefly comprises entrance hallway, dual aspect lounge with French doors leading to sun r...")
               .propertySubType("Detached")
               .bedrooms(4)
               .propertyImages(PropertyImages.builder().mainMapImageSrc("https://media.rightmove.co.uk:443/dir/crop/10:9-16:9/9k/8115/72367257/8115_10184969_IMG_01_0000_max_296x197.jpg").build())
               .fullPropertyUrl("https://www.rightmove.co.uk/property-to-rent/property-72367257.html")
               .daysOnMarket(50)
               .build();

      List<Property> properties = new ArrayList<>();
      properties.add(property1);
      properties.add(property2);

      final LettingPropertiesTrackingResult build = LettingPropertiesTrackingResult.builder()
               .numberOfPropertiesLet(2)
               .letProperties(properties)
               .build();

      emailService.sendLettingReportsEmail(build);
      return ResponseEntity.status(HttpStatus.OK).body(String.format("Email sent..!!!"));

   }
}
