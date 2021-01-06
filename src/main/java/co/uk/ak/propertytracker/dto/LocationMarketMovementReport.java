package co.uk.ak.propertytracker.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LocationMarketMovementReport
{
   private Long locationId;

   private String locationName;

   private long numberOfLetProperties;

   private List<PropertyDto> letProperties;

   private long numberOfSoldProperties;

   private List<PropertyDto> soldProperties;

   private int numberOfNewProperties;

   private List<PropertyDto> newProperties;
}
