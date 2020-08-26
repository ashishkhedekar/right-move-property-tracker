package co.uk.ak.propertytracker.service.impl;

import co.uk.ak.propertytracker.dto.PropertyDto;
import co.uk.ak.propertytracker.mapper.PropertyDtoToPropertyModelMapper;
import co.uk.ak.propertytracker.model.PropertyModel;
import co.uk.ak.propertytracker.repository.PropertyRepository;
import co.uk.ak.propertytracker.service.PropertyDao;
import co.uk.ak.propertytracker.strategy.ChangeDetector;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

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
      LOG.info("Number of change detectors are [{}]", changeDetectors.size() );

      final Optional<PropertyModel> propertyModelOptional = propertyRepository.findByPropertyId(Long.valueOf(propertyDto.getId()));
      if (propertyModelOptional.isPresent())
      {
         LOG.info("Property model found, going to update some attributes");
         final PropertyModel propertyModel = propertyModelOptional.get();
         changeDetectors.forEach(cd -> cd.detectAndPersist(propertyModel, propertyDto));
         propertyModel.setLastPropertyUpdateReceived(DateTime.now().toDate());
         propertyModel.setDaysOnMarket(calculateDaysOnMarket(propertyModel));
         propertyRepository.save(propertyModel);
      }
      else
      {
         LOG.info("Going to save property model for the first time");
         final PropertyModel propertyModel = propertyDtoToPropertyModelMapper.propertyDtoPropertyModelMapper(propertyDto);
         propertyRepository.save(propertyModel);
      }
   }

   private int calculateDaysOnMarket(final PropertyModel propertyModel)
   {
      return Period.between(propertyModel.getFirstVisibleDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now()).getDays();
   }
}
