package co.uk.ak.propertytracker.facade.impl;

import co.uk.ak.propertytracker.endpoints.searchcriteriadto.SearchCriteriaDto;
import co.uk.ak.propertytracker.facade.SearchCriteriaFacade;
import co.uk.ak.propertytracker.mapper.SearchCriteriaMapper;
import co.uk.ak.propertytracker.model.LettingsSearchCriteriaModel;
import co.uk.ak.propertytracker.model.SalesSearchCriteriaModel;
import co.uk.ak.propertytracker.model.SearchCriteriaModel;
import co.uk.ak.propertytracker.repository.SearchCriteriaRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DefaultSearchCriteriaFacade implements SearchCriteriaFacade
{
   private static final Logger LOG = LoggerFactory.getLogger(DefaultSearchCriteriaFacade.class);

   private final SearchCriteriaMapper searchCriteriaMapper;
   private final SearchCriteriaRepository searchCriteriaRepository;

   @Override
   public void save(final SearchCriteriaDto searchCriteriaDto)
   {
      //check if search criteria already exists
      final SearchCriteriaModel searchCriteriaModel = searchCriteriaMapper.toSearchCriteriaModel(searchCriteriaDto);
      searchCriteriaRepository.save(searchCriteriaModel);
   }

   @Override
   public List<SearchCriteriaDto> getAll()
   {
      final List<SearchCriteriaDto> searchCriteriaDtos = new ArrayList<>();
      searchCriteriaRepository.findAll()
               .forEach(e -> {
                  LOG.debug("The type of object received has channel [{}] and class is [{}] ", e.getChannel(), e.getClass().getName());
                  if (e instanceof LettingsSearchCriteriaModel)
                  {
                     searchCriteriaDtos.add(searchCriteriaMapper.lettingsSearchCriteriaModelToSearchCriteriaDto((LettingsSearchCriteriaModel) e));
                  }
                  else
                  {
                     searchCriteriaDtos.add(searchCriteriaMapper.salesSearchCriteriaModelToSearchCriteriaDto((SalesSearchCriteriaModel) e));
                  }
               });
      return searchCriteriaDtos;
   }
}
