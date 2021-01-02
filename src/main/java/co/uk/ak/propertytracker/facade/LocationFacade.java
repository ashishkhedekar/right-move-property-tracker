package co.uk.ak.propertytracker.facade;

import co.uk.ak.propertytracker.endpoints.dtos.PropertyWsDto;

import java.util.Date;
import java.util.Set;

public interface LocationFacade {

	Set<PropertyWsDto> findRecentlyOffMarketProperties(final String locationIdentifier, final Date offMarketDate);
}
