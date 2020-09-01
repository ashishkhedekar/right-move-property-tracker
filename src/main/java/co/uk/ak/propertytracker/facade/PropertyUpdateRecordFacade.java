package co.uk.ak.propertytracker.facade;

import co.uk.ak.propertytracker.dto.Channel;
import co.uk.ak.propertytracker.dto.PropertyUpdateRecordDto;

import java.util.Date;
import java.util.Map;
import java.util.Set;

public interface PropertyUpdateRecordFacade
{
   Map<String, Set<PropertyUpdateRecordDto>> getStats(Date reportStartDate, Channel channel, String type);
}
