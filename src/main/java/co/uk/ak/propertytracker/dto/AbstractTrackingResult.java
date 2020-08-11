package co.uk.ak.propertytracker.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public abstract class AbstractTrackingResult
{
   private int newProperties;

   private int numberOfPropertiesReduced;

   public abstract boolean needsReporting();
}
