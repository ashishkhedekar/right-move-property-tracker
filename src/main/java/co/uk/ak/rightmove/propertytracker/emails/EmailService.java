package co.uk.ak.rightmove.propertytracker.emails;

import co.uk.ak.rightmove.propertytracker.dto.LettingPropertiesTrackingResult;

public interface EmailService
{
   void sendLettingReportsEmail(LettingPropertiesTrackingResult trackingResult);

}
