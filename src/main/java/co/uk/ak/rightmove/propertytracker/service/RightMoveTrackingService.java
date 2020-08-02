package co.uk.ak.rightmove.propertytracker.service;

import co.uk.ak.rightmove.propertytracker.dto.RightMoveResult;
import co.uk.ak.rightmove.propertytracker.dto.TrackingResult;

public interface RightMoveTrackingService
{
   TrackingResult trackProperties(RightMoveResult rightMoveResult);

}
