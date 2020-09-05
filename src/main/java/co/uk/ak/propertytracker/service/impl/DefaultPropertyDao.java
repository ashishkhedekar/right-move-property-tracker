package co.uk.ak.propertytracker.service.impl;

import co.uk.ak.propertytracker.dto.PropertyDto;
import co.uk.ak.propertytracker.mapper.PropertyDtoToPropertyModelMapper;
import co.uk.ak.propertytracker.model.PropertyModel;
import co.uk.ak.propertytracker.model.PropertyUpdateRecordModel;
import co.uk.ak.propertytracker.repository.PropertyRepository;
import co.uk.ak.propertytracker.service.PropertyDao;
import co.uk.ak.propertytracker.strategy.ChangeDetector;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DefaultPropertyDao implements PropertyDao
{
   private static final Logger LOG = LoggerFactory.getLogger(DefaultPropertyDao.class);

   private final PropertyRepository propertyRepository;
   private final PropertyDtoToPropertyModelMapper propertyDtoToPropertyModelMapper;

   private final List<ChangeDetector> changeDetectors;

   @Override
   @Transactional
   public void createOrUpdate(final PropertyDto propertyDto)
   {
      final Optional<PropertyModel> propertyModelOptional = propertyRepository.findByPropertyId(propertyDto.getId());
      if (propertyModelOptional.isPresent())
      {
         LOG.info("Property model found for property id [{}], going to update some attributes", propertyDto.getId());
         final PropertyModel propertyModel = propertyModelOptional.get();
         final List<PropertyUpdateRecordModel> updateRecordModels = changeDetectors.stream()
                  .map(cd -> cd.detectAndPersist(propertyModel, propertyDto))
                  .filter(Objects::nonNull)
                  .collect(Collectors.toList());

         propertyModel.getPropertyUpdateRecords().addAll(updateRecordModels);
         propertyModel.setLastPropertyUpdateReceived(DateTime.now().toDate());
         propertyModel.setDaysOnMarket(calculateDaysOnMarket(propertyModel));
         propertyRepository.save(propertyModel);
      }
      else
      {
         LOG.info("Going to save property model with property_id [{}] for the first time", propertyDto.getId());
         final PropertyModel propertyModel = propertyDtoToPropertyModelMapper.propertyDtoPropertyModelMapper(propertyDto);
         propertyDto.setRegistered(true);
         final List<PropertyUpdateRecordModel> updateRecordModels = changeDetectors.stream()
                  .map(cd -> cd.detectAndPersist(propertyModel, propertyDto))
                  .filter(Objects::nonNull)
                  .collect(Collectors.toList());

         propertyModel.getPropertyUpdateRecords().addAll(updateRecordModels);
         propertyRepository.save(propertyModel);
      }
   }

   private int calculateDaysOnMarket(final PropertyModel propertyModel)
   {
      return Period.between(propertyModel.getFirstVisibleDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now()).getDays();
   }

   @Override
   public PropertyDto findProperty(Long propertyId)
   {
      final Optional<PropertyModel> propertyModel = propertyRepository.findByPropertyId(propertyId);
      return propertyModel.map(propertyDtoToPropertyModelMapper::propertyModelPropertyDtoMapper).orElse(null);
   }
}
