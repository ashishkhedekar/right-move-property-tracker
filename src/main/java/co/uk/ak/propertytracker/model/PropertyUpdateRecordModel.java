package co.uk.ak.propertytracker.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class PropertyUpdateRecordModel
{
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

   private String field;
   private String oldValue;
   private String newValue;
   private Date CreationDate;

   @ManyToOne(fetch = FetchType.LAZY)
   private PropertyModel property;

   public Long getId()
   {
      return id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

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

   public Date getCreationDate()
   {
      return CreationDate;
   }

   public void setCreationDate(Date creationDate)
   {
      CreationDate = creationDate;
   }
}
