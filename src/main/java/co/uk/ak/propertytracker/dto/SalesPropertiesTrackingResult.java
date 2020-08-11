package co.uk.ak.propertytracker.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
public class SalesPropertiesTrackingResult extends AbstractTrackingResult
{
   private int numberOfPropertiesSold;

   @Override
   public boolean needsReporting()
   {
      return getNumberOfPropertiesSold() > 0 || getNewProperties() > 0;
   }
}
