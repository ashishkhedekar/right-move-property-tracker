package co.uk.ak.rightmove.propertytracker.mapper;

import co.uk.ak.rightmove.propertytracker.rightmove.dto.Property;
import co.uk.ak.rightmove.propertytracker.model.RightMovePropertyModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RightMovePropertyMapper
{
   @Mapping(target = "firstVisibleDate", source = "firstVisibleDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
   RightMovePropertyModel propertyToPropertyModel(Property property);
}
