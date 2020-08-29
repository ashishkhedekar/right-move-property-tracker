package co.uk.ak.propertytracker.endpoints.controller;

import co.uk.ak.propertytracker.dto.MarketMovementReport;
import co.uk.ak.propertytracker.dto.PropertyDto;
import co.uk.ak.propertytracker.dto.PropertyImagesDto;
import co.uk.ak.propertytracker.emails.EmailService;
import co.uk.ak.propertytracker.endpoints.searchcriteriadto.SearchCriteriaDto;
import co.uk.ak.propertytracker.facade.DummyFacade;
import co.uk.ak.propertytracker.facade.SearchCriteriaFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

//TODO Delete this class?
@Controller
@AllArgsConstructor
public class TrialController
{
   private SearchCriteriaFacade searchCriteriaFacade;
   private final DummyFacade dummyFacade;
   private final EmailService emailService;

   @GetMapping(path = "/search-criteria")
   public ResponseEntity<String> getAllSearchCriteria()
   {
      final List<SearchCriteriaDto> all = searchCriteriaFacade.getAll();
      return ResponseEntity.status(HttpStatus.OK).body("All good, found " + all.size() + " criteria");
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

      final PropertyDto propertyDto1 = PropertyDto.builder()
               .displayStatus("Let agreed")
               .displayAddress("Buckingham")
               .summary("Student HMO Property 2020/2021 Academic Year - This 5 double bedroom property benefits from two bathrooms, a large fully fitted kitchen , common room , patio garden and driveway parking. The property has the Buckingham University campus on its doorstep and the town centre within a minute...")
               .propertySubType("Terraced")
               .bedrooms(5)
               .firstVisibleDate("2020-05-05T15:35:03Z")
               .propertyImages(PropertyImagesDto.builder().mainMapImageSrc("https://media.rightmove.co.uk:443/dir/crop/10:9-16:9/70k/69152/91967702/69152_2548866_IMG_01_0001_max_296x197.jpg").build())
               .fullPropertyUrl("https://www.rightmove.co.uk/property-to-rent/property-91967702.html")
               .build();

      final PropertyDto propertyDto2 = PropertyDto.builder()
               .displayAddress("24 Needlepin Way, Buckingham")
               .summary("Well presented four bedroom DETACHED house situated on the popular Lace Hill development. The property benefits from being IN CATCHMENT FOR LOCAL SCHOOLS and WALKING DISTANCE TO THE TOWN CENTRE. The property briefly comprises entrance hallway, dual aspect lounge with French doors leading to sun r...")
               .propertySubType("Detached")
               .bedrooms(4)
               .firstVisibleDate("2020-07-05T15:35:03Z")
               .propertyImages(PropertyImagesDto.builder().mainMapImageSrc("https://media.rightmove.co.uk:443/dir/crop/10:9-16:9/9k/8115/72367257/8115_10184969_IMG_01_0000_max_296x197.jpg").build())
               .fullPropertyUrl("https://www.rightmove.co.uk/property-to-rent/property-72367257.html")
               .premiumListing(true)
               .build();

      List<PropertyDto> properties = new ArrayList<>();
      properties.add(propertyDto1);
      properties.add(propertyDto2);

      final MarketMovementReport build = MarketMovementReport.builder()
               .numberOfOffMarketProperties(2)
               .offMarketProperties(properties)
               .build();

      emailService.sendHourlyMarketMovementReportEmail(build);
      return ResponseEntity.status(HttpStatus.OK).body(String.format("Email sent..!!!"));

   }
}
