package co.uk.ak.propertytracker.emails;

import co.uk.ak.propertytracker.dto.MarketMovementReport;

public interface EmailService
{
   void sendLettingReportsEmail(MarketMovementReport trackingResult);

}
