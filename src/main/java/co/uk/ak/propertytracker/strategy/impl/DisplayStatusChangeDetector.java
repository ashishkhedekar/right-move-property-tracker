package co.uk.ak.propertytracker.strategy.impl;

import co.uk.ak.propertytracker.dto.PropertyDto;
import co.uk.ak.propertytracker.model.PropertyModel;
import co.uk.ak.propertytracker.strategy.ChangeDetector;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class DisplayStatusChangeDetector implements ChangeDetector
{
   public Predicate<PropertyModel> propertyChangedPredicate(final PropertyDto propertyDto)
   {
      return model -> (!StringUtils.equalsIgnoreCase(propertyDto.getDisplayStatus(), model.getDisplayStatus()));
   }

   @Override
   public String dtoFieldName()
   {
      return "displayStatus";
   }

   @Override
   public String modelFieldName()
   {
      return "displayStatus";
   }
}
