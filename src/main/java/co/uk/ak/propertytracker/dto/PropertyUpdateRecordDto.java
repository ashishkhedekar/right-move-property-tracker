package co.uk.ak.propertytracker.dto;

import lombok.Data;

@Data
public class PropertyUpdateRecordDto
{
   private String field;

   private String oldValue;

   private String newValue;

   private long propertyId;

   private String locationIdentifier;
}
