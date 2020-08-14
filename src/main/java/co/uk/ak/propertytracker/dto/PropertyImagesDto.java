package co.uk.ak.propertytracker.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PropertyImagesDto
{
   private List<PropertyImageDto> images = null;
   private String mainImageSrc;
   private String mainMapImageSrc;
}
