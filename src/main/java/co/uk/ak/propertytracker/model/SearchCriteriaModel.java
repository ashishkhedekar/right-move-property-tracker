package co.uk.ak.propertytracker.model;

import javax.persistence.*;

@Entity
@Table(name = "search_criteria")
@Inheritance(strategy = InheritanceType.JOINED)
public class SearchCriteriaModel
{
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

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
   private boolean isFetching;

   public Long getId()
   {
      return id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   public String getLocationIdentifier()
   {
      return locationIdentifier;
   }

   public void setLocationIdentifier(String locationIdentifier)
   {
      this.locationIdentifier = locationIdentifier;
   }

   public int getMinBedrooms()
   {
      return minBedrooms;
   }

   public void setMinBedrooms(int minBedrooms)
   {
      this.minBedrooms = minBedrooms;
   }

   public long getMaxPrice()
   {
      return maxPrice;
   }

   public void setMaxPrice(long maxPrice)
   {
      this.maxPrice = maxPrice;
   }

   public int getNumberOfPropertiesPerPage()
   {
      return numberOfPropertiesPerPage;
   }

   public void setNumberOfPropertiesPerPage(int numberOfPropertiesPerPage)
   {
      this.numberOfPropertiesPerPage = numberOfPropertiesPerPage;
   }

   public float getRadius()
   {
      return radius;
   }

   public void setRadius(float radius)
   {
      this.radius = radius;
   }

   public int getSortType()
   {
      return sortType;
   }

   public void setSortType(int sortType)
   {
      this.sortType = sortType;
   }

   public int getIndex()
   {
      return index;
   }

   public void setIndex(int index)
   {
      this.index = index;
   }

   public String getViewType()
   {
      return viewType;
   }

   public void setViewType(String viewType)
   {
      this.viewType = viewType;
   }

   public String getAreaSizeUnit()
   {
      return areaSizeUnit;
   }

   public void setAreaSizeUnit(String areaSizeUnit)
   {
      this.areaSizeUnit = areaSizeUnit;
   }

   public String getCurrencyCode()
   {
      return currencyCode;
   }

   public void setCurrencyCode(String currencyCode)
   {
      this.currencyCode = currencyCode;
   }

   public boolean isFetching()
   {
      return isFetching;
   }

   public void setFetching(boolean fetching)
   {
      isFetching = fetching;
   }
}
