package co.uk.ak.propertytracker.facade;

import co.uk.ak.propertytracker.dto.LocationDto;
import co.uk.ak.propertytracker.endpoints.dtos.PropertyWsDto;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface LocationFacade {

	List<LocationDto> getAllLocations();

	Set<PropertyWsDto> findRecentlyOffMarketProperties(final Long locationIdentifier, final Date offMarketDate);
}
