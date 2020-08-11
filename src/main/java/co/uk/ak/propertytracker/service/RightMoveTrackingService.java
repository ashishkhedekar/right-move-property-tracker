package co.uk.ak.propertytracker.service;

import co.uk.ak.propertytracker.dto.LettingPropertiesTrackingResult;
import co.uk.ak.propertytracker.dto.RightMoveResult;

public interface RightMoveTrackingService
{
   LettingPropertiesTrackingResult trackProperties(RightMoveResult rightMoveResult);

   void refreshProperties(RightMoveResult rightMoveResult);
}
