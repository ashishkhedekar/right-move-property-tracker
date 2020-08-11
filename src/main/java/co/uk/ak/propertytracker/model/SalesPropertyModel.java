package co.uk.ak.propertytracker.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="sales_properties")
public class SalesPropertyModel extends PropertyModel
{
   // Optional fields
   @Column(nullable = true)
   private Date propertySoldDate;

   public Date getPropertySoldDate()
   {
      return propertySoldDate;
   }

   public void setPropertySoldDate(Date propertySoldDate)
   {
      this.propertySoldDate = propertySoldDate;
   }
}
