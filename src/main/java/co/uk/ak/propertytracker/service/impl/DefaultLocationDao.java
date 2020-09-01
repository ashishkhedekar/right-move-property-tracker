package co.uk.ak.propertytracker.service.impl;

import co.uk.ak.propertytracker.dto.LocationDto;
import co.uk.ak.propertytracker.mapper.LocationMapper;
import co.uk.ak.propertytracker.model.LocationModel;
import co.uk.ak.propertytracker.model.PropertyModel;
import co.uk.ak.propertytracker.repository.LocationRepository;
import co.uk.ak.propertytracker.repository.PropertyRepository;
import co.uk.ak.propertytracker.service.LocationDao;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class DefaultLocationDao implements LocationDao
{
   private static final Logger LOG = LoggerFactory.getLogger(DefaultLocationDao.class);
   private final LocationRepository locationRepository;
   private final LocationMapper locationMapper;
   private final PropertyRepository propertyRepository;

   @Override
   public LocationModel getOrCreate(final LocationDto locationDto)
   {
      final Optional<LocationModel> locationModel = locationRepository.findByLocationIdentifier(locationDto.getLocationIdentifier());
      if (locationModel.isPresent())
      {
         return locationModel.get();
      }
      else
      {
         final LocationModel locationModelToSave = locationMapper.locationDtoToLocationModel(locationDto);
         return locationRepository.save(locationModelToSave);
      }
   }

   @Override
   public void associateProperty(final String locationIdentifier, final Long propertyId)
   {
      final Optional<LocationModel> locationModelOptional = locationRepository.findByLocationIdentifier(locationIdentifier);
      final Optional<PropertyModel> propertyModelOptional = propertyRepository.findByPropertyId(propertyId);
      if (locationModelOptional.isPresent() && propertyModelOptional.isPresent())
      {
         LOG.info("Location found for location identifier [{}]", locationIdentifier);
         final LocationModel locationModel = locationModelOptional.get();
         locationModel.getProperties().add(propertyModelOptional.get());
         locationRepository.save(locationModel);
      }
      else
      {
         if (locationModelOptional.isEmpty())
         {
            LOG.warn("Location NOT found for location identifier [{}]", locationIdentifier);
         }
         if (propertyModelOptional.isEmpty())
         {
            LOG.warn("Property NOT found for id [{}]", propertyId);
         }
      }
   }

   @Override
   public LocationModel findLocationForProperty(PropertyModel propertyModel)
   {
      final Optional<LocationModel> locationModelOptional = locationRepository.findByPropertiesPropertyId(propertyModel.getPropertyId());
      if (locationModelOptional.isPresent())
      {
         return locationModelOptional.get();
      }
      else
      {
         final LocationModel unknownLocation = new LocationModel();
         unknownLocation.setLocationIdentifier("UNKNOWN");
         return unknownLocation;
      }
   }
}
