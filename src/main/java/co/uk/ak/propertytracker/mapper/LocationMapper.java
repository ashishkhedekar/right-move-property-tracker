package co.uk.ak.propertytracker.mapper;

import co.uk.ak.propertytracker.dto.LocationDto;
import co.uk.ak.propertytracker.model.LocationModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = PropertyDtoToPropertyModelMapper.class)
public interface LocationMapper
{
   LocationModel locationDtoToLocationModel(LocationDto locationDto);

   LocationDto locationModelToLocationDto(LocationModel locationModel);

}
