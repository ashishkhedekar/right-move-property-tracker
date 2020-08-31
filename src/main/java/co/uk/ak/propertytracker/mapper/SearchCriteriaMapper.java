package co.uk.ak.propertytracker.mapper;

import co.uk.ak.propertytracker.dto.Channel;
import co.uk.ak.propertytracker.endpoints.searchcriteriadto.SearchCriteriaDto;
import co.uk.ak.propertytracker.model.LettingsSearchCriteriaModel;
import co.uk.ak.propertytracker.model.SalesSearchCriteriaModel;
import co.uk.ak.propertytracker.model.SearchCriteriaModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SearchCriteriaMapper
{
   default SearchCriteriaModel toSearchCriteriaModel(SearchCriteriaDto searchCriteriaDto)
   {
      if (searchCriteriaDto != null && searchCriteriaDto.getChannel() != null && searchCriteriaDto.getChannel() == Channel.RENT)
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

   @Mapping(target = "locationIdentifier", source = "location.locationIdentifier")
   SearchCriteriaDto lettingsSearchCriteriaModelToSearchCriteriaDto(LettingsSearchCriteriaModel lettingsSearchCriteriaModel);
   @Mapping(target = "locationIdentifier", source = "location.locationIdentifier")
   SearchCriteriaDto salesSearchCriteriaModelToSearchCriteriaDto(SalesSearchCriteriaModel salesSearchCriteriaModel);
}
