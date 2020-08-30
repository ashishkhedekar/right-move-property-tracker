package co.uk.ak.propertytracker.model;

import javax.persistence.*;

@Entity
@Table(name = "search_criteria")
@Inheritance(strategy = InheritanceType.JOINED)
public class SearchCriteriaModel extends AbstractModel
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
   @OneToOne
   private LocationModel location;

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

   public String getChannel()
   {
      return channel;
   }

   public void setChannel(String channel)
   {
      this.channel = channel;
   }

   public boolean isFetching()
   {
      return isFetching;
   }

   public void setFetching(boolean fetching)
   {
      isFetching = fetching;
   }

   public LocationModel getLocation()
   {
      return location;
   }

   public void setLocation(LocationModel location)
   {
      this.location = location;
   }
}
