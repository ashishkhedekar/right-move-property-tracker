package co.uk.ak.propertytracker.mapper;

import co.uk.ak.propertytracker.rightmove.dto.RightMoveProperty;
import co.uk.ak.propertytracker.model.RightMovePropertyModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RightMovePropertyMapper
{
   @Mapping(target = "firstVisibleDate", source = "firstVisibleDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
   RightMovePropertyModel propertyToPropertyModel(RightMoveProperty rightMoveProperty);
}
