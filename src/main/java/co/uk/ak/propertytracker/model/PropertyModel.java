package co.uk.ak.propertytracker.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "properties")
@Inheritance(strategy = InheritanceType.JOINED)
public class PropertyModel
{
   @Id
   private Long id;

   //Mandatory fields
   private Integer bedrooms;
   @Lob
   private String summary;
   private String displayAddress;
   private String propertySubType;
   private Date firstVisibleDate;

   // Optional fields
   @Column(nullable = true)
   private String displayStatus;
   @Column(nullable = true)
   private Long daysOnMarket;
   @Column(nullable = true)
   private Boolean premiumListing;

   public Long getId()
   {
      return id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   public Integer getBedrooms()
   {
      return bedrooms;
   }

   public void setBedrooms(Integer bedrooms)
   {
      this.bedrooms = bedrooms;
   }

   public String getSummary()
   {
      return summary;
   }

   public void setSummary(String summary)
   {
      this.summary = summary;
   }

   public String getDisplayAddress()
   {
      return displayAddress;
   }

   public void setDisplayAddress(String displayAddress)
   {
      this.displayAddress = displayAddress;
   }

   public String getPropertySubType()
   {
      return propertySubType;
   }

   public void setPropertySubType(String propertySubType)
   {
      this.propertySubType = propertySubType;
   }

   public Date getFirstVisibleDate()
   {
      return firstVisibleDate;
   }

   public void setFirstVisibleDate(Date firstVisibleDate)
   {
      this.firstVisibleDate = firstVisibleDate;
   }

   public String getDisplayStatus()
   {
      return displayStatus;
   }

   public void setDisplayStatus(String displayStatus)
   {
      this.displayStatus = displayStatus;
   }

   public Long getDaysOnMarket()
   {
      return daysOnMarket;
   }

   public void setDaysOnMarket(Long daysOnMarket)
   {
      this.daysOnMarket = daysOnMarket;
   }

   public Boolean getPremiumListing()
   {
      return premiumListing;
   }

   public void setPremiumListing(Boolean premiumListing)
   {
      this.premiumListing = premiumListing;
   }

   ////   @OneToOne
//   private RightMovePriceModel price;
//   private String propertyUrl;
//   private String firstVisibleDate;

//   private String addedOrReduced;
//   private Boolean isRecent;
//   private String formattedDistance;
}
