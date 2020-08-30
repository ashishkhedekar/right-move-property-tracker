package co.uk.ak.propertytracker.mapper;

import co.uk.ak.propertytracker.dto.LocationDto;
import co.uk.ak.propertytracker.model.LocationModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper
{
   LocationModel locationDtoToLocationModel(LocationDto locationDto);

}
