package co.uk.ak.propertytracker.facade;

import co.uk.ak.propertytracker.dto.Channel;

import java.util.Date;

public interface PropertyUpdateRecordFacade
{
   int getStats(Date reportStartDate, Channel channel, String type);
}
