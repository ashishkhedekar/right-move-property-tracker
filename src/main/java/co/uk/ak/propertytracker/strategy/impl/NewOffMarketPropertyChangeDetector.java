package co.uk.ak.propertytracker.strategy.impl;

import co.uk.ak.propertytracker.dto.PropertyDto;
import co.uk.ak.propertytracker.model.PropertyModel;
import co.uk.ak.propertytracker.strategy.ChangeDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class NewOffMarketPropertyChangeDetector implements ChangeDetector
{
   private static final Logger LOG = LoggerFactory.getLogger(NewOffMarketPropertyChangeDetector.class);
   @Override
   public Predicate<PropertyModel> propertyChangedPredicate(PropertyDto propertyDto)
   {
      return model -> (propertyDto.getDisplayStatus().equalsIgnoreCase("Let Agreed") ||
              propertyDto.getDisplayStatus().equalsIgnoreCase("Sold STC")) &&
              model.getOffMarketDate() == null;
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

   @Override
   public Class<?> modelFieldType()
   {
      return String.class;
   }
}
