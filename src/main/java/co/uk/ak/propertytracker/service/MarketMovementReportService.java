package co.uk.ak.propertytracker.service;

import co.uk.ak.propertytracker.dto.LettingPropertiesTrackingResult;

import java.util.Date;

public interface MarketMovementReportService
{
   LettingPropertiesTrackingResult generateMarketMovementReport(Date reportStartTime);
}
