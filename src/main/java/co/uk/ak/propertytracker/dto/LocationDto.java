package co.uk.ak.propertytracker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class LocationDto
{
   private Long id;
   private String locationIdentifier;
   private String description;

}
