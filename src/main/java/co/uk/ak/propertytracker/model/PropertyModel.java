package co.uk.ak.propertytracker.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "properties")
@Inheritance(strategy = InheritanceType.JOINED)
public class PropertyModel extends AbstractModel
{
   private Long propertyId;

   //Mandatory fields
   private Integer bedrooms;
   @Column(length=350)
   private String summary;
   private String displayAddress;
   private String propertySubType;
   private Date firstVisibleDate;
   private String displayPrice;
   //todo need mapping
   private String propertyType;
   private String propertyUrl;
   private String channel;
   private Integer amount;

   // Optional fields
   @Column(nullable = true)
   private String displayStatus;
   @Column(nullable = true)
   private Long daysOnMarket;
   @Column(nullable = true)
   private Boolean premiumListing;
   private String mainMapImageSrc;

   @OneToMany(
            mappedBy = "property",
            cascade = CascadeType.ALL,
            orphanRemoval = true
   )
   private List<PropertyUpdateRecordModel> propertyUpdateRecords;

   public Long getPropertyId()
   {
      return propertyId;
   }

   public void setPropertyId(Long propertyId)
   {
      this.propertyId = propertyId;
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

   public String getDisplayPrice()
   {
      return displayPrice;
   }

   public void setDisplayPrice(String displayPrice)
   {
      this.displayPrice = displayPrice;
   }

   public String getPropertyType()
   {
      return propertyType;
   }

   public void setPropertyType(String propertyType)
   {
      this.propertyType = propertyType;
   }

   public String getPropertyUrl()
   {
      return propertyUrl;
   }

   public void setPropertyUrl(String propertyUrl)
   {
      this.propertyUrl = propertyUrl;
   }

   public String getChannel()
   {
      return channel;
   }

   public void setChannel(String channel)
   {
      this.channel = channel;
   }

   public Integer getAmount()
   {
      return amount;
   }

   public void setAmount(Integer amount)
   {
      this.amount = amount;
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

   public List<PropertyUpdateRecordModel> getPropertyUpdateRecords()
   {
      return propertyUpdateRecords;
   }

   public void setPropertyUpdateRecords(List<PropertyUpdateRecordModel> propertyUpdateRecords)
   {
      this.propertyUpdateRecords = propertyUpdateRecords;
   }

   public String getMainMapImageSrc()
   {
      return mainMapImageSrc;
   }

   public void setMainMapImageSrc(String mainMapImageSrc)
   {
      this.mainMapImageSrc = mainMapImageSrc;
   }

   ////   @OneToOne
//   private RightMovePriceModel price;
//   private String propertyUrl;
//   private String firstVisibleDate;

//   private String addedOrReduced;
//   private Boolean isRecent;
//   private String formattedDistance;
}
