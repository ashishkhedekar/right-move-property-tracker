package co.uk.ak.propertytracker.mapper;

import co.uk.ak.propertytracker.dto.PropertyUpdateRecordDto;
import co.uk.ak.propertytracker.model.PropertyUpdateRecordModel;
import co.uk.ak.propertytracker.service.LocationDao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = { LocationDao.class } )
public abstract class PropertyUpdateRecordModelMapper
{
   @Autowired
   LocationDao locationDao;

   @Mapping(target = "propertyId", source = "property.propertyId")
   @Mapping(target = "locationIdentifier", expression = "java(locationDao.findLocationForProperty(model.getProperty()).getLocationIdentifier())")
   public abstract PropertyUpdateRecordDto propertyUpdateRecordModelToDto(PropertyUpdateRecordModel model);
}
