package co.uk.ak.propertytracker.service;

import co.uk.ak.propertytracker.dto.LocationDto;

public interface LocationService
{
   LocationDto findLocationByLocationIdentifier(final String locationIdentifier);
}
