package co.uk.ak.propertytracker.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
public abstract class AbstractTrackingResult
{
   private int numberOfNewPropertiesOnMarket;

   private List<PropertyDto>  newPropertiesOnMarket;

   public abstract boolean needsReporting();
}
