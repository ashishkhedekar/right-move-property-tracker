package co.uk.ak.propertytracker.mapper;

import co.uk.ak.propertytracker.dto.PropertyDto;
import co.uk.ak.propertytracker.dto.PropertyImagesDto;
import co.uk.ak.propertytracker.rightmove.dto.RightMoveDisplayPrice;
import co.uk.ak.propertytracker.rightmove.dto.RightMoveProperty;
import co.uk.ak.propertytracker.rightmove.dto.RightMovePropertyImages;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.util.CollectionUtils;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RightMovePropertyToPropertyDtoMapper
{
   @Mapping(target = "propertyImages", source = "rightMovePropertyImages")
   @Mapping(target = "displayPrice", expression = "java(displayPrice(rightMoveProperty))")
   @Mapping(target = "amount", source = "rightMovePrice.amount")
   PropertyDto rightMovePropertyToPropertyModel(RightMoveProperty rightMoveProperty);

   PropertyImagesDto rightMovePropertyImagesToPropertyImagesDto(final RightMovePropertyImages rightMovePropertyImages);

   default String displayPrice(final RightMoveProperty rightMoveProperty)
   {
      if (rightMoveProperty.getRightMovePrice() != null && !CollectionUtils.isEmpty(rightMoveProperty.getRightMovePrice().getRightMoveDisplayPrices()))
      {
         return rightMoveProperty.getRightMovePrice().getRightMoveDisplayPrices().stream()
                  .map(RightMoveDisplayPrice::getDisplayPrice)
                  .collect(Collectors.joining("/"));
      }
      return "";
   }

}
