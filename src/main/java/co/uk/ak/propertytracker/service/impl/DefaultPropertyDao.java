package co.uk.ak.propertytracker.service.impl;

import co.uk.ak.propertytracker.dto.PropertyDto;
import co.uk.ak.propertytracker.mapper.PropertyDtoToPropertyModelMapper;
import co.uk.ak.propertytracker.model.PropertyModel;
import co.uk.ak.propertytracker.model.PropertyUpdateRecordModel;
import co.uk.ak.propertytracker.repository.PropertyRepository;
import co.uk.ak.propertytracker.service.PropertyDao;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DefaultPropertyDao implements PropertyDao
{
   private static final Logger LOG = LoggerFactory.getLogger(DefaultPropertyDao.class);

   private final PropertyRepository propertyRepository;
   private final PropertyDtoToPropertyModelMapper propertyDtoToPropertyModelMapper;

   @Override
   public void save(final PropertyDto propertyDto)
   {
      final Optional<PropertyModel> propertyModelOptional = propertyRepository.findByPropertyId(Long.valueOf(propertyDto.getId()));
      if (propertyModelOptional.isPresent())
      {
         LOG.info("Property model found, going to update some attributes");
         final PropertyModel propertyModel = propertyModelOptional.get();
         if (!StringUtils.equalsIgnoreCase(propertyDto.getDisplayStatus(), propertyModel.getDisplayStatus()))
         {
            final PropertyUpdateRecordModel propertyUpdateRecordModel = new PropertyUpdateRecordModel();
            propertyUpdateRecordModel.setProperty(propertyModel);
            propertyUpdateRecordModel.setField("displayStatus");
            propertyUpdateRecordModel.setOldValue(propertyModel.getDisplayStatus());
            propertyUpdateRecordModel.setNewValue(propertyDto.getDisplayStatus());
            propertyModel.getPropertyUpdateRecords().add(propertyUpdateRecordModel);

            propertyModel.setDisplayStatus(propertyDto.getDisplayStatus());
            propertyModel.setModificationTime(DateTime.now().toDate());
            propertyRepository.save(propertyModel);
            LOG.info("Display Status saved successfully");
         }
         else
         {
            LOG.info("Display Status did not change");
         }
      }
      else
      {
         LOG.info("Going to save property model for the first time");
         final PropertyModel propertyModel = propertyDtoToPropertyModelMapper.propertyDtoPropertyModelMapper(propertyDto);
         propertyRepository.save(propertyModel);
      }
   }
}
