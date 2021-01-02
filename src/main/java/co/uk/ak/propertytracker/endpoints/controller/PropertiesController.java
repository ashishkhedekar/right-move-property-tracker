package co.uk.ak.propertytracker.endpoints.controller;

import co.uk.ak.propertytracker.endpoints.dtos.SearchCriteriaDto;
import co.uk.ak.propertytracker.facade.PropertiesTrackerFacade;
import co.uk.ak.propertytracker.facade.SearchCriteriaFacade;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class PropertiesController
{
   private static final Logger LOG = LoggerFactory.getLogger(PropertiesController.class);

   private final PropertiesTrackerFacade trackerFacade;
   private final SearchCriteriaFacade searchCriteriaFacade;

   @GetMapping("/")
   public ResponseEntity<String> home()
   {
      return ResponseEntity.status(HttpStatus.OK).body("App is up and running");
   }

   @GetMapping("/properties-to-let")
   public ResponseEntity<String> message(@RequestParam String locationId)
   {
      // Used for testing
      if (locationId.equalsIgnoreCase("leeds") || locationId.equalsIgnoreCase("london"))
      {
            searchCriteriaFacade.getAll().stream()
                  .filter(e -> e.getLocationIdentifier().equalsIgnoreCase(locationId))
                  .findFirst()
                  .ifPresent(trackerFacade::trackProperties);
      }
      else
      {
         // Generating report for all search results
         LOG.info("Generating report for all search results");

         final List<SearchCriteriaDto> searchCriteriaAll = searchCriteriaFacade.getAll();
         LOG.info("Found [{}] search criteria ", searchCriteriaAll.size());
         searchCriteriaAll
                  .stream()
                  .peek(e -> LOG.info("Tracking properties for criteris [{}] ", e.getLocationIdentifier()))
                  .forEach(trackerFacade::trackProperties);
      }
      return ResponseEntity.status(HttpStatus.OK).body("This app is doing some important stuff");
   }

   @GetMapping("/mark-off-market-properties")
   public ResponseEntity<String> findAndMarkOffMarketProperties()
   {
      trackerFacade.findAndMarkOffMarketProperties();
      return ResponseEntity.status(HttpStatus.OK).body("Properties marked off market");
   }


}
