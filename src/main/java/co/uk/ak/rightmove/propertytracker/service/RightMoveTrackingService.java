package co.uk.ak.rightmove.propertytracker.service;

import co.uk.ak.rightmove.propertytracker.dto.LettingPropertiesTrackingResult;
import co.uk.ak.rightmove.propertytracker.dto.RightMoveResult;

public interface RightMoveTrackingService
{
   LettingPropertiesTrackingResult trackProperties(RightMoveResult rightMoveResult);

   void refreshProperties(RightMoveResult rightMoveResult);
}
