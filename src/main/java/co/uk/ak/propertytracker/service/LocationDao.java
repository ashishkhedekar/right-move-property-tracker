package co.uk.ak.propertytracker.service;

import co.uk.ak.propertytracker.dto.LocationDto;
import co.uk.ak.propertytracker.model.LocationModel;
import co.uk.ak.propertytracker.model.PropertyModel;

public interface LocationDao
{
   LocationModel getOrCreate(LocationDto locationDto);

   void associateProperty(String locationIdentifier, Long propertyId);

   LocationModel findLocationForProperty(PropertyModel propertyModel);
}
