package co.uk.ak.propertytracker.endpoints.controller;

import co.uk.ak.propertytracker.endpoints.searchcriteriadto.SearchCriteriaDto;
import co.uk.ak.propertytracker.facade.PropertiesTrackerFacade;
import co.uk.ak.propertytracker.facade.SearchCriteriaFacade;
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
            LOG.info("Getting properties for location [{}]", locationId);
            SearchCriteriaDto searchCriteria = new SearchCriteriaDto();
            searchCriteria.setId(1L);
            searchCriteria.setLocationIdentifier(locationId);
            trackerFacade.trackProperties(searchCriteria);
      }
      else
      {
         // Generating report for all search results
         LOG.info("Generating report for all search results");

         final List<SearchCriteriaDto> searchCriteriaFacadeAll = searchCriteriaFacade.getAll();
         LOG.info("Found [{}] search criteria ", searchCriteriaFacadeAll.size());
         searchCriteriaFacadeAll
                  .stream()
                  .peek(e -> LOG.info("Tracking properties for criteris [{}] ", e.getLocationIdentifier()))
                  .forEach(trackerFacade::trackProperties);
      }
      return ResponseEntity.status(HttpStatus.OK).body("This app is doing some important stuff");
   }

   @PostMapping(path = "/search-criteria")
   public ResponseEntity<String> createSearchCriteria(@RequestBody final SearchCriteriaDto searchCriteriaDto)
   {
      searchCriteriaFacade.save(searchCriteriaDto);
      return ResponseEntity.status(HttpStatus.OK).body("Your search criteria was successfully saved");
   }
}
