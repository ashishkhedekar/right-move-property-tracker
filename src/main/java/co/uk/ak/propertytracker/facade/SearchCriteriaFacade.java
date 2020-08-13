package co.uk.ak.propertytracker.facade;

import co.uk.ak.propertytracker.endpoints.searchcriteriadto.SearchCriteriaDto;

import java.util.List;

public interface SearchCriteriaFacade
{
   void save(SearchCriteriaDto searchCriteriaDto);

   List<SearchCriteriaDto> getAll();
}
