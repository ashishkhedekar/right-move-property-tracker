package co.uk.ak.propertytracker.service;

import co.uk.ak.propertytracker.dto.PropertyDto;

public interface PropertyDao
{
   void save(PropertyDto propertyDto);
}
