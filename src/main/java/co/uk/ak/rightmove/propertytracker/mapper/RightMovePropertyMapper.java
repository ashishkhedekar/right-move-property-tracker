package co.uk.ak.rightmove.propertytracker.mapper;

import co.uk.ak.rightmove.propertytracker.dto.Property;
import co.uk.ak.rightmove.propertytracker.model.RightMovePropertyModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RightMovePropertyMapper
{
   RightMovePropertyModel propertyToPropertyModel(Property property);
}
