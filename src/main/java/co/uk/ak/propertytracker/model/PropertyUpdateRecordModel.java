package co.uk.ak.propertytracker.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class PropertyUpdateRecordModel extends AbstractModel
{
   private String field;

   private String oldValue;

   private String newValue;

   @ManyToOne(fetch = FetchType.LAZY)
   private PropertyModel property;

   public String getField()
   {
      return field;
   }

   public void setField(String field)
   {
      this.field = field;
   }

   public String getOldValue()
   {
      return oldValue;
   }

   public void setOldValue(String oldValue)
   {
      this.oldValue = oldValue;
   }

   public String getNewValue()
   {
      return newValue;
   }

   public void setNewValue(String newValue)
   {
      this.newValue = newValue;
   }

   public PropertyModel getProperty()
   {
      return property;
   }

   public void setProperty(PropertyModel property)
   {
      this.property = property;
   }
}
