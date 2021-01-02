package co.uk.ak.propertytracker.endpoints.controller;

import co.uk.ak.propertytracker.endpoints.dtos.SearchCriteriaDto;
import co.uk.ak.propertytracker.facade.SearchCriteriaFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@AllArgsConstructor
public class SearchCriteriaController
{
   private final SearchCriteriaFacade searchCriteriaFacade;

   @PostMapping(path = "/search-criteria")
   public ResponseEntity<String> createSearchCriteria(@RequestBody final SearchCriteriaDto searchCriteriaDto)
   {
      searchCriteriaFacade.save(searchCriteriaDto);
      return ResponseEntity.status(HttpStatus.OK).body("Your search criteria was successfully saved");
   }
}
