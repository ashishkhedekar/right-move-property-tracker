package co.uk.ak.propertytracker.service;

import co.uk.ak.propertytracker.dto.MarketMovementReport;

import java.util.Date;

public interface MarketMovementReportService
{
   MarketMovementReport generateMarketMovementReport(Date reportStartTime, String channel);
}
