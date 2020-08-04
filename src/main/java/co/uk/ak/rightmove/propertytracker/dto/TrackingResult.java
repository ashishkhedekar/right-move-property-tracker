package co.uk.ak.rightmove.propertytracker.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TrackingResult
{
   private int numberOfPropertiesLet;

   private int numberOfPropertiesReduced;

   private int newProperties;
}
