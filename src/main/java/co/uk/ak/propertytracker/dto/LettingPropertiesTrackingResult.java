package co.uk.ak.propertytracker.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class LettingPropertiesTrackingResult extends AbstractTrackingResult
{
   private int numberOfLetProperties;

   private List<PropertyDto> letProperties;

   public boolean needsReporting()
   {
      return getNumberOfLetProperties() > 0 || getNumberOfNewPropertiesOnMarket() > 0;
   }
}
