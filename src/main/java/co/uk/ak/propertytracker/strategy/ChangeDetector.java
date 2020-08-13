package co.uk.ak.propertytracker.strategy;

import co.uk.ak.propertytracker.dto.PropertyDto;
import co.uk.ak.propertytracker.model.PropertyModel;
import co.uk.ak.propertytracker.model.PropertyUpdateRecordModel;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Predicate;

import static org.springframework.util.StringUtils.capitalize;

public interface ChangeDetector
{
   Logger LOG = LoggerFactory.getLogger(ChangeDetector.class);

   default PropertyModel detect(final PropertyModel propertyModel, final PropertyDto propertyDto)
   {
      try
      {
         if (propertyChangedPredicate(propertyDto).test(propertyModel))
         {
            final PropertyUpdateRecordModel propertyUpdateRecordModel = new PropertyUpdateRecordModel();
            propertyUpdateRecordModel.setProperty(propertyModel);
            propertyUpdateRecordModel.setField(modelFieldName());
            propertyUpdateRecordModel.setOldValue(modelGetMethod().invoke(propertyModel).toString());
            propertyUpdateRecordModel.setNewValue(dtoGetMethod().invoke(propertyDto).toString());
            propertyModel.getPropertyUpdateRecords().add(propertyUpdateRecordModel);

            modelSetMethod().invoke(propertyModel, dtoGetMethod().invoke(propertyDto).toString());
            propertyModel.setModificationTime(DateTime.now().toDate());
         }
      }
      catch (IllegalAccessException|InvocationTargetException e)
      {
         LOG.warn("Something went wrong while detecting change for property [{}] ", propertyDto.getId());
      }
      return propertyModel;
   }

   Predicate<PropertyModel> propertyChangedPredicate(final PropertyDto propertyDto);

   String dtoFieldName();

   String modelFieldName();

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
         return PropertyModel.class.getMethod("set" + capitalize(modelFieldName()), String.class);
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
