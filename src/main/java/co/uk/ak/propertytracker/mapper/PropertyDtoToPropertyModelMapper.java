package co.uk.ak.propertytracker.mapper;

import co.uk.ak.propertytracker.dto.PropertyDto;
import co.uk.ak.propertytracker.model.PropertyModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

@Mapper(componentModel = "spring")
public interface PropertyDtoToPropertyModelMapper
{
   @Mapping(target = "firstVisibleDate", source = "firstVisibleDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
   @Mapping(target = "id", source = "propertyId")
   @Mapping(target = "fullPropertyUrl", expression = "java(fullPropertyUrl(propertyModel))")
   @Mapping(target = "mainMapImageSrc", source = "mainMapImageSrc")
   PropertyDto propertyModelPropertyDtoMapper(final PropertyModel propertyModel);

   @Mapping(target = "firstVisibleDate", source = "firstVisibleDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
   @Mapping(target = "propertyId", source = "id")
   @Mapping(target = "mainMapImageSrc", source = "propertyImages.mainMapImageSrc")
   PropertyModel propertyDtoPropertyModelMapper(final PropertyDto propertyDto);

   default String fullPropertyUrl(final PropertyModel propertyModel)
   {
      return "www.rightmove.com" + propertyModel.getPropertyUrl();
   }

   @AfterMapping
   default void calculateDaysOnMarket(final PropertyDto propertyDto, @MappingTarget final PropertyModel propertyModel)
   {
      if (propertyModel.isOnMarket())
      {
         final int daysOnMarket = Period.between(propertyModel.getFirstVisibleDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now()).getDays();
         propertyModel.setDaysOnMarket(daysOnMarket);
      }
   }
}
