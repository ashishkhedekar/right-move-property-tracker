package co.uk.ak.propertytracker.mapper;

import co.uk.ak.propertytracker.endpoints.dtos.PropertyWsDto;
import co.uk.ak.propertytracker.model.PropertyModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PropertyModelTpPropertyWsDtoMapper {

	PropertyWsDto propertyModelPropertyWsDtoMapper(final PropertyModel propertyModel);
}
