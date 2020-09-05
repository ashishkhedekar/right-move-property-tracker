package co.uk.ak.propertytracker.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LocationMarketMovementReport
{
   private String locationIdentifier;

   private String locationName;

   private int numberOfLetProperties;

   private List<PropertyDto> letProperties;

   private int numberOfSoldProperties;

   private List<PropertyDto> soldProperties;

   private int numberOfNewProperties;

   private List<PropertyDto> newProperties;
}
