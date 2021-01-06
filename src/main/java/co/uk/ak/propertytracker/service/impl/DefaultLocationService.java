package co.uk.ak.propertytracker.service.impl;

import co.uk.ak.propertytracker.dto.LocationDto;
import co.uk.ak.propertytracker.mapper.LocationMapper;
import co.uk.ak.propertytracker.model.LocationModel;
import co.uk.ak.propertytracker.repository.LocationRepository;
import co.uk.ak.propertytracker.service.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DefaultLocationService implements LocationService
{
   private final LocationRepository locationRepository;
   private final LocationMapper locationMapper;

   @Override
   @Transactional
   public LocationDto findLocationByLocationIdentifier(String locationIdentifier)
   {
      final Optional<LocationModel> locationModelOptional = locationRepository.findById(locationIdentifier);
      return locationModelOptional.map(locationMapper::locationModelToLocationDto).orElse(null);
   }
}
