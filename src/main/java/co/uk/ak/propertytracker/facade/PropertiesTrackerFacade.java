package co.uk.ak.propertytracker.facade;

import co.uk.ak.propertytracker.dto.LettingPropertiesTrackingResult;
import co.uk.ak.propertytracker.endpoints.searchcriteriadto.SearchCriteriaDto;

public interface RightMovePropertiesTrackerFacade
{
   LettingPropertiesTrackingResult trackProperties(SearchCriteriaDto searchCriteria);
   void trackPropertiesV2(final SearchCriteriaDto searchCriteria);
}
