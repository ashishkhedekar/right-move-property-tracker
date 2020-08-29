package co.uk.ak.propertytracker.emails;

import co.uk.ak.propertytracker.dto.MarketMovementReport;

public interface EmailService
{
   void sendSomethingWentWrongEmail(String message);

   void sendHourlyMarketMovementReportEmail(MarketMovementReport trackingResult);

   void sendDailyOffMarketPropertiesReportEmail(MarketMovementReport marketMovementReport);

}
