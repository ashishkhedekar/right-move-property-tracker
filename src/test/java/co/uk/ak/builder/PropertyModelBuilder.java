package co.uk.ak.builder;

import co.uk.ak.propertytracker.model.PropertyModel;

public class PropertyModelBuilder
{

   private long id;

   private int bedrooms;

   private String propertySubType;

   private String displayAddress;

   private String displayStatus;

   public static PropertyModelBuilder builder()
   {
      return new PropertyModelBuilder();
   }

   public PropertyModelBuilder id(long id)
   {
      this.id = id;
      return this;
   }

   public PropertyModelBuilder bedrooms(int bedrooms)
   {
      this.bedrooms = bedrooms;
      return this;
   }

   public PropertyModelBuilder propertySubType(String propertySubType)
   {
      this.propertySubType = propertySubType;
      return this;
   }

   public PropertyModelBuilder displayAddress(String displayAddress)
   {
      this.displayAddress = displayAddress;
      return this;
   }

   public PropertyModelBuilder displayStatus(String displayStatus)
   {
      this.displayStatus = displayStatus;
      return this;
   }

   public PropertyModel build()
   {
      final PropertyModel model = new PropertyModel();
      model.setId(this.id);
      model.setBedrooms(this.bedrooms);
      model.setPropertySubType(this.propertySubType);
      model.setDisplayAddress(this.displayAddress);
      model.setDisplayStatus(this.displayStatus);
      return model;
   }
}
