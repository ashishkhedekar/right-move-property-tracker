package co.uk.ak.propertytracker.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Data
@Builder
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
   private Channel channel;
   private String firstVisibleDate;
   private String heading;
   private Boolean enhancedListing;
   private String propertyTypeFullDescription;
   private String displayStatus;
   private String addedOrReduced;
   private Boolean isRecent;
   private Integer numberOfImages;
   private PropertyImagesDto propertyImages;

   private String fullPropertyUrl;
   private String mainMapImageSrc;

   public int daysOnMarket()
   {
      return Period.between(LocalDate.parse(firstVisibleDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")), LocalDate.now()).getDays();
   }
}
