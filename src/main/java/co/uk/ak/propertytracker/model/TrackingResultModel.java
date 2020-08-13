package co.uk.ak.propertytracker.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tracking_results")
public class TrackingResultModel extends AbstractModel
{
   private int numberOfPropertiesLet;
   private int numberOfPropertiesReduced;
   private int numberOfPropertiesSold;
   private Date createdDate;

   @OneToMany(fetch = FetchType.LAZY)
   private List<PropertyModel> letProperties;

   @OneToMany(fetch = FetchType.LAZY)
   private List<PropertyModel> reducedProperties;

   @OneToMany(fetch = FetchType.LAZY)
   private List<PropertyModel> soldProperties;

   public int getNumberOfPropertiesLet()
   {
      return numberOfPropertiesLet;
   }

   public void setNumberOfPropertiesLet(int numberOfPropertiesLet)
   {
      this.numberOfPropertiesLet = numberOfPropertiesLet;
   }

   public int getNumberOfPropertiesReduced()
   {
      return numberOfPropertiesReduced;
   }

   public void setNumberOfPropertiesReduced(int numberOfPropertiesReduced)
   {
      this.numberOfPropertiesReduced = numberOfPropertiesReduced;
   }

   public int getNumberOfPropertiesSold()
   {
      return numberOfPropertiesSold;
   }

   public void setNumberOfPropertiesSold(int numberOfPropertiesSold)
   {
      this.numberOfPropertiesSold = numberOfPropertiesSold;
   }

   public Date getCreatedDate()
   {
      return createdDate;
   }

   public void setCreatedDate(Date createdDate)
   {
      this.createdDate = createdDate;
   }

   public List<PropertyModel> getLetProperties()
   {
      return letProperties;
   }

   public void setLetProperties(List<PropertyModel> letProperties)
   {
      this.letProperties = letProperties;
   }

   public List<PropertyModel> getReducedProperties()
   {
      return reducedProperties;
   }

   public void setReducedProperties(List<PropertyModel> reducedProperties)
   {
      this.reducedProperties = reducedProperties;
   }

   public List<PropertyModel> getSoldProperties()
   {
      return soldProperties;
   }

   public void setSoldProperties(List<PropertyModel> soldProperties)
   {
      this.soldProperties = soldProperties;
   }
}
