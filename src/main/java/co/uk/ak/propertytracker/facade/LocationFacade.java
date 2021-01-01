package co.uk.ak.propertytracker.facade;

import co.uk.ak.propertytracker.dto.PropertyDto;

import java.util.Date;
import java.util.Set;

public interface LocationFacade {

	Set<PropertyDto> findRecentlyOffMarketProperties(final String locationIdentifier, final Date offMarketDate);
}
