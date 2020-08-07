package co.uk.ak.rightmove.propertytracker.facade;

import co.uk.ak.rightmove.propertytracker.dto.LettingPropertiesTrackingResult;

public interface RightMovePropertiesTrackerFacade
{
   LettingPropertiesTrackingResult trackProperties(String locationId);
}
