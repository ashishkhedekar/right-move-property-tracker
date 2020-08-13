package co.uk.ak.propertytracker.dto;

import lombok.Data;

@Data
public class PropertyDto
{
   private Integer id;
   private Integer bedrooms;
   private String summary;
   private String displayAddress;
   private String countryCode;
   private String propertySubType;
   private Boolean premiumListing;
   private Boolean featuredProperty;
   private Integer amount;
   private String displayPrice;
   private Boolean commercial;
   private Boolean development;
   private Boolean residential;
   private Boolean students;
   private String displaySize;
   private String propertyUrl;
   private String contactUrl;
   private String channel;
   private String firstVisibleDate;
   private String heading;
   private Boolean enhancedListing;
   private String propertyTypeFullDescription;
   private String displayStatus;
   private String addedOrReduced;
   private Boolean isRecent;

   private Integer numberOfImages;
   private PropertyImagesDto propertyImages;
}
