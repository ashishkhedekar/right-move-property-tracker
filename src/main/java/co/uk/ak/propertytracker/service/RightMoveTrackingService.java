package co.uk.ak.propertytracker.service;

import co.uk.ak.propertytracker.dto.MarketMovementReport;
import co.uk.ak.propertytracker.rightmove.dto.RightMoveResult;

public interface RightMoveTrackingService
{
   MarketMovementReport trackProperties(RightMoveResult rightMoveResult);

   void refreshProperties(RightMoveResult rightMoveResult);
}
