package co.uk.ak.propertytracker.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
public class SalesPropertiesTrackingResult extends AbstractTrackingResult
{
   private int numberOfSoldProperties;

   private List<PropertyDto> soldProperties;

   @Override
   public boolean needsReporting()
   {
      return getNumberOfSoldProperties() > 0 || getNumberOfNewPropertiesOnMarket() > 0;
   }
}
