package co.uk.ak.propertytracker.endpoints.controller;

import co.uk.ak.propertytracker.endpoints.searchcriteriadto.SearchCriteriaDto;
import co.uk.ak.propertytracker.facade.SearchCriteriaFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

//TODO Delete this class?
@Controller
@AllArgsConstructor
public class TrialController
{
   private SearchCriteriaFacade searchCriteriaFacade;

   @GetMapping(path = "/search-criteria")
   public ResponseEntity<String> getAllSearchCriteria()
   {
      final List<SearchCriteriaDto> all = searchCriteriaFacade.getAll();
      return ResponseEntity.status(HttpStatus.OK).body("All good, found " + all.size() + " criteria");
   }

}
