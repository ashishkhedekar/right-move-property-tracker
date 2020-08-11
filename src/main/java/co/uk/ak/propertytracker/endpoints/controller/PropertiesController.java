package co.uk.ak.propertytracker.endpoints.controller;

import co.uk.ak.propertytracker.dto.LettingPropertiesTrackingResult;
import co.uk.ak.propertytracker.emails.EmailService;
import co.uk.ak.propertytracker.endpoints.searchcriteriadto.SearchCriteriaDto;
import co.uk.ak.propertytracker.facade.DummyFacade;
import co.uk.ak.propertytracker.facade.RightMovePropertiesTrackerFacade;
import co.uk.ak.propertytracker.facade.DefaultSearchCriteriaFacade;
import co.uk.ak.propertytracker.rightmove.dto.RightMoveProperty;
import co.uk.ak.propertytracker.rightmove.dto.RightMovePropertyImages;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class PropertiesController
{
   private static final Logger LOG = LoggerFactory.getLogger(PropertiesController.class);

   private final RightMovePropertiesTrackerFacade trackerFacade;

   private final DummyFacade dummyFacade;
   private final EmailService emailService;
   private final DefaultSearchCriteriaFacade defaultSearchCriteriaFacade;

   @GetMapping("/properties-to-let")
   public ResponseEntity<String> message(@RequestParam String locationId)
   {
      if (locationId.equalsIgnoreCase("leeds") || locationId.equalsIgnoreCase("london"))
      {
         LOG.info("Getting properties for location [{}]", locationId);
         trackerFacade.trackProperties(locationId);
      }
      else
      {
         LOG.info("Getting properties for default loation [{}] ", "REGION%5E239");
         trackerFacade.trackProperties("REGION%5E239");
      }
      return ResponseEntity.status(HttpStatus.OK).body("This app is doing some important stuff");
   }

   @PostMapping(path = "/search-criteria")
   public ResponseEntity<String> createSearchCriteria(@RequestBody final SearchCriteriaDto searchCriteriaDto)
   {
      defaultSearchCriteriaFacade.save(searchCriteriaDto);
      return ResponseEntity.status(HttpStatus.OK).body("Your search criteria was successfully saved");
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
      RightMoveProperty rightMoveProperty1 = RightMoveProperty.builder()
               .displayStatus("Let agreed")
               .displayAddress("Buckingham")
               .summary("Student HMO Property 2020/2021 Academic Year - This 5 double bedroom property benefits from two bathrooms, a large fully fitted kitchen , common room , patio garden and driveway parking. The property has the Buckingham University campus on its doorstep and the town centre within a minute...")
               .propertySubType("Terraced")
               .bedrooms(5)
               .rightMovePropertyImages(RightMovePropertyImages.builder().mainMapImageSrc("https://media.rightmove.co.uk:443/dir/crop/10:9-16:9/70k/69152/91967702/69152_2548866_IMG_01_0001_max_296x197.jpg").build())
               .fullPropertyUrl("https://www.rightmove.co.uk/property-to-rent/property-91967702.html")
               .daysOnMarket(10)
//               .premiumListing(true)
               .build();

      RightMoveProperty rightMoveProperty2 = RightMoveProperty.builder()
//               .displayStatus("Let agreed")
               .displayAddress("24 Needlepin Way, Buckingham")
               .summary("Well presented four bedroom DETACHED house situated on the popular Lace Hill development. The property benefits from being IN CATCHMENT FOR LOCAL SCHOOLS and WALKING DISTANCE TO THE TOWN CENTRE. The property briefly comprises entrance hallway, dual aspect lounge with French doors leading to sun r...")
               .propertySubType("Detached")
               .bedrooms(4)
               .rightMovePropertyImages(RightMovePropertyImages.builder().mainMapImageSrc("https://media.rightmove.co.uk:443/dir/crop/10:9-16:9/9k/8115/72367257/8115_10184969_IMG_01_0000_max_296x197.jpg").build())
               .fullPropertyUrl("https://www.rightmove.co.uk/property-to-rent/property-72367257.html")
               .daysOnMarket(50)
               .premiumListing(true)
               .build();

      List<RightMoveProperty> properties = new ArrayList<>();
      properties.add(rightMoveProperty1);
      properties.add(rightMoveProperty2);

      final LettingPropertiesTrackingResult build = LettingPropertiesTrackingResult.builder()
               .numberOfPropertiesLet(2)
               .letProperties(properties)
               .build();

      emailService.sendLettingReportsEmail(build);
      return ResponseEntity.status(HttpStatus.OK).body(String.format("Email sent..!!!"));

   }
}
