package co.uk.ak.propertytracker.mapper;

import co.uk.ak.propertytracker.dto.PropertyDto;
import co.uk.ak.propertytracker.model.PropertyModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PropertyDtoToPropertyModelMapper
{
   @Mapping(target = "firstVisibleDate", source = "firstVisibleDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
   PropertyDto propertyModelPropertyDtoMapper(final PropertyModel propertyModel);

   @Mapping(target = "firstVisibleDate", source = "firstVisibleDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
   PropertyModel propertyDtoPropertyModelMapper(final PropertyDto propertyDto);
}
