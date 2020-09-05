package co.uk.ak.propertytracker.strategy.impl;

import co.uk.ak.propertytracker.dto.PropertyDto;
import co.uk.ak.propertytracker.model.PropertyModel;
import co.uk.ak.propertytracker.strategy.ChangeDetector;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class NewPropertyChangeDetector implements ChangeDetector
{
   @Override
   public Predicate<PropertyModel> propertyChangedPredicate(PropertyDto propertyDto)
   {
      return model -> BooleanUtils.isNotTrue(model.getRegistered()) && BooleanUtils.isTrue(propertyDto.getRegistered());
   }

   @Override
   public String dtoFieldName()
   {
      return "registered";
   }

   @Override
   public String modelFieldName()
   {
      return "registered";
   }

   @Override
   public Class<?> modelFieldType()
   {
      return Boolean.class;
   }
}
