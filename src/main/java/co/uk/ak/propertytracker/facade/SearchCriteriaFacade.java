package co.uk.ak.propertytracker.facade;

import co.uk.ak.propertytracker.endpoints.dtos.SearchCriteriaDto;

import java.util.List;
import java.util.Optional;

public interface SearchCriteriaFacade
{
   void save(SearchCriteriaDto searchCriteriaDto);

   List<SearchCriteriaDto> getAll();

   Optional<SearchCriteriaDto> findByLocationIdentifier();
}
