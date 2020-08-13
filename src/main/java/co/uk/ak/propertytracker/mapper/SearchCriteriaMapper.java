package co.uk.ak.propertytracker.mapper;

import co.uk.ak.propertytracker.endpoints.searchcriteriadto.SearchCriteriaDto;
import co.uk.ak.propertytracker.model.LettingsSearchCriteriaModel;
import co.uk.ak.propertytracker.model.SalesSearchCriteriaModel;
import co.uk.ak.propertytracker.model.SearchCriteriaModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SearchCriteriaMapper
{
   default SearchCriteriaModel toSearchCriteriaModel(SearchCriteriaDto searchCriteriaDto)
   {
      if (searchCriteriaDto != null && searchCriteriaDto.getChannel() != null && searchCriteriaDto.getChannel().equalsIgnoreCase("RENT"))
      {
         return toLettingsSearchCriteriaModel(searchCriteriaDto);
      }
      else
      {
         return toSalesSearchCriteriaModel(searchCriteriaDto);
      }
   }

   LettingsSearchCriteriaModel toLettingsSearchCriteriaModel(SearchCriteriaDto searchCriteriaDto);
   SalesSearchCriteriaModel toSalesSearchCriteriaModel(SearchCriteriaDto searchCriteriaDto);

   SearchCriteriaDto lettingsSearchCriteriaModelToSearchCriteriaDto(LettingsSearchCriteriaModel lettingsSearchCriteriaModel);
   SearchCriteriaDto salesSearchCriteriaModelToSearchCriteriaDto(SalesSearchCriteriaModel salesSearchCriteriaModel);
}
