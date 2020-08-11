package co.uk.ak.propertytracker.emails;

import co.uk.ak.propertytracker.dto.LettingPropertiesTrackingResult;

public interface EmailService
{
   void sendLettingReportsEmail(LettingPropertiesTrackingResult trackingResult);

}
