package co.uk.ak.propertytracker.facade.impl;

import co.uk.ak.propertytracker.endpoints.searchcriteriadto.SearchCriteriaDto;
import co.uk.ak.propertytracker.facade.SearchCriteriaFacade;
import co.uk.ak.propertytracker.mapper.SearchCriteriaMapper;
import co.uk.ak.propertytracker.model.SearchCriteriaModel;
import co.uk.ak.propertytracker.repository.SearchCriteriaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DefaultSearchCriteriaFacade implements SearchCriteriaFacade
{
   private final SearchCriteriaMapper searchCriteriaMapper;
   private final SearchCriteriaRepository searchCriteriaRepository;

   @Override
   public void save(final SearchCriteriaDto searchCriteriaDto)
   {
      //check if search criteria already exists
      final SearchCriteriaModel searchCriteriaModel = searchCriteriaMapper.toSearchCriteriaModel(searchCriteriaDto);
      searchCriteriaRepository.save(searchCriteriaModel);
   }
}
