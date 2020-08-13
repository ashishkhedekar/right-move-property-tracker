package co.uk.ak.propertytracker.endpoints.searchcriteriadto;

import lombok.Data;

@Data
public class SearchCriteriaDto
{
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
   private String channel = "RENT";
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
               "&viewType=" + viewType +
               "&channel=" + channel +
               "&areaSizeUnit=" + areaSizeUnit  +
               "&currencyCode=" + currencyCode +
               "&isFetching=" + isFetching;

      //"locationIdentifier=%s&minBedrooms=3&maxPrice=25000&
      // numberOfPropertiesPerPage=24&radius=0.0
      // &sortType=6&
      // index=0&includeLetAgreed=true&viewType=LIST&channel=RENT&areaSizeUnit=sqft&currencyCode=GBP&isFetching=false";



   }
}
