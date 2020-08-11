package co.uk.ak.propertytracker.endpoints.searchcriteriadto;

import lombok.Data;

@Data
public class SearchCriteriaDto
{
   private String locationIdentifier;
   private int minBedrooms;
   private long maxPrice;
   private int numberOfPropertiesPerPage;
   private float radius;
   private int sortType;
   private int index;
   private String viewType;
   private String areaSizeUnit;
   private String currencyCode;
   private String channel;
   private boolean isFetching;

   //Lettings
   private boolean includeLetAgreed;

   //Sales
   private boolean includeSSTC;
}
