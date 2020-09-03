package co.uk.ak.propertytracker.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "locations")
@Inheritance(strategy = InheritanceType.JOINED)
public class LocationModel extends AbstractModel
{
   private String locationIdentifier;
   private String description;
   @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL )
   private Set<PropertyModel> properties;

   public String getLocationIdentifier()
   {
      return locationIdentifier;
   }

   public void setLocationIdentifier(String locationIdentifier)
   {
      this.locationIdentifier = locationIdentifier;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription(String description)
   {
      this.description = description;
   }

   public Set<PropertyModel> getProperties()
   {
      return properties;
   }

   public void setProperties(Set<PropertyModel> properties)
   {
      this.properties = properties;
   }
}
