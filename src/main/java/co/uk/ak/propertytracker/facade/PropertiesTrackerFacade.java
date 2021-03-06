package co.uk.ak.propertytracker.facade;

import co.uk.ak.propertytracker.endpoints.dtos.SearchCriteriaDto;

public interface PropertiesTrackerFacade
{
   void trackProperties(final SearchCriteriaDto searchCriteria);

   void findAndMarkOffMarketProperties();
}
