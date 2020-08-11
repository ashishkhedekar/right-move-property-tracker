package co.uk.ak.propertytracker.dto;

import co.uk.ak.propertytracker.rightmove.dto.RightMoveProperty;
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
   private List<RightMoveProperty> letProperties;

   public boolean needsReporting()
   {
      return getNumberOfPropertiesLet() > 0 || getNewProperties() > 0;
   }
}
