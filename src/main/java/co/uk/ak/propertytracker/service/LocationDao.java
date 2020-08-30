package co.uk.ak.propertytracker.service;

import co.uk.ak.propertytracker.dto.LocationDto;
import co.uk.ak.propertytracker.model.LocationModel;

public interface LocationDao
{
   LocationModel getOrCreate(LocationDto locationDto);
}
