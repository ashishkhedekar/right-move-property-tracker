package co.uk.ak.propertytracker.service.impl;

import co.uk.ak.propertytracker.dto.LocationDto;
import co.uk.ak.propertytracker.mapper.LocationMapper;
import co.uk.ak.propertytracker.model.LocationModel;
import co.uk.ak.propertytracker.repository.LocationRepository;
import co.uk.ak.propertytracker.service.LocationDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DefaultLocationDao implements LocationDao
{
   private final LocationRepository locationRepository;
   private final LocationMapper locationMapper;

   @Override
   public LocationModel getOrCreate(LocationDto locationDto)
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
}
