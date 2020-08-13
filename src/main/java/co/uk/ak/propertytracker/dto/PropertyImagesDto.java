package co.uk.ak.propertytracker.dto;

import lombok.Data;

import java.util.List;

@Data
public class PropertyImagesDto
{
   private List<PropertyImageDto> rightMoveImages = null;
   private String mainImageSrc;
   private String mainMapImageSrc;
}
