package co.uk.ak.propertytracker.endpoints.searchcriteriadto;

import co.uk.ak.propertytracker.dto.Channel;
import lombok.Data;

@Data
public class SearchCriteriaDto
{
   private Long id;
   private String locationIdentifier;
   private int minBedrooms;
   private long maxPrice = 10000000;
   private int numberOfPropertiesPerPage = 24;
   private float radius = 0.0f;
   private int sortType = 6;
   private int index = 0;
   private String viewType = "LIST";
   private String areaSizeUnit = "sqft";
   private String currencyCode = "GBP";
   private Channel channel = Channel.RENT;
   private boolean isFetching = false;

   //Lettings
   private boolean includeLetAgreed;

   //Sales
   private boolean includeSSTC;

   public String buildQueryString()
   {
      return "locationIdentifier=" + locationIdentifier +
               "&minBedrooms=" + minBedrooms +
               "&maxPrice=" + maxPrice +
               "&numberOfPropertiesPerPage=" + numberOfPropertiesPerPage +
               "&sortType=" + sortType +
               "&index=" + index +
               "&includeLetAgreed=" + includeLetAgreed +
               "&includeSSTC=" + includeSSTC +
               "&viewType=" + viewType +
               "&channel=" + channel +
               "&areaSizeUnit=" + areaSizeUnit  +
               "&currencyCode=" + currencyCode +
               "&isFetching=" + isFetching;
   }
}
