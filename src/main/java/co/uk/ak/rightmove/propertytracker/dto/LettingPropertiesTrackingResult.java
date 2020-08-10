package co.uk.ak.rightmove.propertytracker.dto;

import co.uk.ak.rightmove.propertytracker.rightmove.dto.Property;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class LettingPropertiesTrackingResult extends AbstractTrackingResult
{
   private int numberOfPropertiesLet;
   private List<Property> letProperties;

   public boolean needsReporting()
   {
      return getNumberOfPropertiesLet() > 0 || getNewProperties() > 0;
   }
}
