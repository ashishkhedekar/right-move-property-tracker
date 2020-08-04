package co.uk.ak.rightmove.propertytracker.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class LettingPropertiesTrackingResult extends AbstractTrackingResult
{
   private int numberOfPropertiesLet;


   public boolean needsReporting()
   {
      return getNumberOfPropertiesLet() > 0 || getNewProperties() > 0;
   }
}
