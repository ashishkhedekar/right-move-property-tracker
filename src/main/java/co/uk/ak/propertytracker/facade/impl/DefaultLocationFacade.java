package co.uk.ak.propertytracker.facade.impl;

import co.uk.ak.propertytracker.dto.LocationDto;
import co.uk.ak.propertytracker.endpoints.dtos.PropertyWsDto;
import co.uk.ak.propertytracker.facade.LocationFacade;
import co.uk.ak.propertytracker.mapper.LocationMapper;
import co.uk.ak.propertytracker.mapper.PropertyModelToPropertyWsDtoMapper;
import co.uk.ak.propertytracker.model.LocationModel;
import co.uk.ak.propertytracker.service.LocationDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DefaultLocationFacade implements LocationFacade {

	private final LocationDao locationDao;
	private final PropertyModelToPropertyWsDtoMapper propertyModelToPropertyWsDtoMapper;
	private final LocationMapper locationMapper;

	@Override
	public List<LocationDto> getAllLocations() {
		return locationDao.getAllLocations()
				.stream()
				.map(locationMapper::locationModelToLocationDto)
				.collect(Collectors.toList());
	}

	@Override
	public Set<PropertyWsDto> findRecentlyOffMarketProperties(final Long locationId,
			final Date offMarketDate, final int minBedrooms, final int maxBedrooms) {

		final Optional<LocationModel> locationForLocationIdentifier = locationDao
				.findLocationForLocationId(locationId);

		return locationForLocationIdentifier.map(locationModel -> locationModel.getProperties().stream()
				.filter(property -> property.getOffMarketDate() != null && property.getOffMarketDate().after(offMarketDate) && !property.isOnMarket())
				.filter(property -> property.getBedrooms() >= minBedrooms & property.getBedrooms() <= maxBedrooms)
				.map(propertyModelToPropertyWsDtoMapper::propertyModelPropertyWsDtoMapper)
				.collect(Collectors.toSet())).orElseGet(Set::of);
	}
}
