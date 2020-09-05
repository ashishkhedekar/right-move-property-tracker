package co.uk.ak.propertytracker.strategy;

import co.uk.ak.propertytracker.dto.PropertyDto;
import co.uk.ak.propertytracker.model.PropertyModel;
import co.uk.ak.propertytracker.model.PropertyUpdateRecordModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Predicate;

import static org.springframework.util.StringUtils.capitalize;

public interface ChangeDetector
{
   Logger LOG = LoggerFactory.getLogger(ChangeDetector.class);

   default PropertyUpdateRecordModel detectAndPersist(final PropertyModel propertyModel, final PropertyDto propertyDto)
   {
      try
      {
         if (propertyChangedPredicate(propertyDto).test(propertyModel))
         {
            LOG.info("Going to create update record");
            final PropertyUpdateRecordModel propertyUpdateRecordModel = new PropertyUpdateRecordModel();
            propertyUpdateRecordModel.setProperty(propertyModel);
            propertyUpdateRecordModel.setField(modelFieldName());
            final Object oldValue = modelGetMethod().invoke(propertyModel);
            propertyUpdateRecordModel.setOldValue(oldValue != null ? oldValue.toString() : null);
            final Object newValue = dtoGetMethod().invoke(propertyDto);
            propertyUpdateRecordModel.setNewValue(newValue != null ? newValue.toString() : null);
            modelSetMethod().invoke(propertyModel, newValue != null ? modelFieldType().cast(newValue): null);
            return propertyUpdateRecordModel;
         }
      }
      catch (IllegalAccessException|InvocationTargetException e)
      {
         LOG.warn("Something went wrong while detecting change for property [{}] ", propertyDto.getId());
      }
      return null;
   }

   Predicate<PropertyModel> propertyChangedPredicate(final PropertyDto propertyDto);

   String dtoFieldName();

   String modelFieldName();

   Class<?> modelFieldType();

   private Method modelGetMethod()
   {
      try
      {
         return PropertyModel.class.getMethod("get"+capitalize(modelFieldName()));
      }
      catch (NoSuchMethodException e)
      {
         throw new RuntimeException("NoSuchMethodException ", e);
      }
   }

   private Method modelSetMethod()
   {
      try
      {
         return PropertyModel.class.getMethod("set" + capitalize(modelFieldName()), modelFieldType());
      }
      catch (NoSuchMethodException e)
      {
         throw new RuntimeException("NoSuchMethodException ", e);
      }
   }

   private Method dtoGetMethod()
   {
      try
      {
         return PropertyDto.class.getMethod("get"+capitalize(dtoFieldName()));
      }
      catch (NoSuchMethodException e)
      {
         throw new RuntimeException("NoSuchMethodException ", e);
      }
   }
}
