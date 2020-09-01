package co.uk.ak.propertytracker.dto;

import lombok.Data;

@Data
public class LocationStats
{
   private String locationName;
   private int days;
   private String channel;
   private String type;
   private int numberOfProperties;
}
