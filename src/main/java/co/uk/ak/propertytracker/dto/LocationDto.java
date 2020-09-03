package co.uk.ak.propertytracker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class LocationDto
{
   private Long id;
   private String locationIdentifier;
   private String description;
   private Set<PropertyDto> properties;

}
