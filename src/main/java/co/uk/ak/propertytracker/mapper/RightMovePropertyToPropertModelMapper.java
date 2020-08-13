package co.uk.ak.propertytracker.mapper;

import co.uk.ak.propertytracker.model.PropertyModel;
import co.uk.ak.propertytracker.rightmove.dto.RightMoveProperty;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RightMovePropertyToPropertModelMapper
{
   @Mapping(target = "firstVisibleDate", source = "firstVisibleDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
   PropertyModel rightMovePropertyToPropertyModel(RightMoveProperty rightMoveProperty);
}
