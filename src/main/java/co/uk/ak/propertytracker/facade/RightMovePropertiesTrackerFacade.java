package co.uk.ak.propertytracker.facade;

import co.uk.ak.propertytracker.dto.LettingPropertiesTrackingResult;

public interface RightMovePropertiesTrackerFacade
{
   LettingPropertiesTrackingResult trackProperties(String locationId);
}
