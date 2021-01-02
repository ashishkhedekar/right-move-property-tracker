package co.uk.ak.propertytracker.facade.impl;

import co.uk.ak.propertytracker.dto.PropertyDto;
import co.uk.ak.propertytracker.endpoints.dtos.PropertyWsDto;
import co.uk.ak.propertytracker.facade.LocationFacade;
import co.uk.ak.propertytracker.mapper.PropertyModelTpPropertyWsDtoMapper;
import co.uk.ak.propertytracker.model.LocationModel;
import co.uk.ak.propertytracker.service.LocationDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DefaultLocationFacade implements LocationFacade {

	private final LocationDao locationDao;
	private final PropertyModelTpPropertyWsDtoMapper propertyModelTpPropertyWsDtoMapper;

	@Override
	public Set<PropertyWsDto> findRecentlyOffMarketProperties(final String locationIdentifier,
			final Date offMarketDate) {

		final Optional<LocationModel> locationForLocationIdentifier = locationDao
				.findLocationForLocationIdentifier(locationIdentifier);

		return locationForLocationIdentifier.map(locationModel -> locationModel.getProperties().stream()
				.filter(property -> property.getOffMarketDate() != null && property.getOffMarketDate().after(offMarketDate) && !property.isOnMarket())
				.map(propertyModelTpPropertyWsDtoMapper::propertyModelPropertyWsDtoMapper)
				.collect(Collectors.toSet())).orElseGet(Set::of);
	}
}
