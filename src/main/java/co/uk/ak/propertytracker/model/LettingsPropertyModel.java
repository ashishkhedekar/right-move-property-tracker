package co.uk.ak.propertytracker.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="lettings_properties")
public class LettingsPropertyModel extends PropertyModel
{

   // Optional fields
   @Column(nullable = true)
   private Date propertyLetDate;

   public Date getPropertyLetDate()
   {
      return propertyLetDate;
   }

   public void setPropertyLetDate(Date propertyLetDate)
   {
      this.propertyLetDate = propertyLetDate;
   }
}
