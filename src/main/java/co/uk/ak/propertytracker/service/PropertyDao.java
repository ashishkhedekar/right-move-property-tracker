package co.uk.ak.propertytracker.service;

import co.uk.ak.propertytracker.dto.PropertyDto;
import co.uk.ak.propertytracker.model.PropertyModel;

public interface PropertyDao
{
   PropertyModel createOrUpdate(PropertyDto propertyDto);

   PropertyDto findProperty(Long propertyId);

}
