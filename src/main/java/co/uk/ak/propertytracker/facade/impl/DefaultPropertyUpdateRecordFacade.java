package co.uk.ak.propertytracker.facade.impl;

import co.uk.ak.propertytracker.dto.Channel;
import co.uk.ak.propertytracker.facade.PropertyUpdateRecordFacade;
import co.uk.ak.propertytracker.model.PropertyUpdateRecordModel;
import co.uk.ak.propertytracker.repository.PropertyUpdateRecordRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class DefaultPropertyUpdateRecordFacade implements PropertyUpdateRecordFacade
{
   private static final Logger LOG = LoggerFactory.getLogger(DefaultPropertyUpdateRecordFacade.class);

   private final PropertyUpdateRecordRepository propertyUpdateRecordRepository;

   @Override
   public int getStats(final Date reportStartDate, final Channel channel, final String type)
   {
      String field = null;
      if (type != null && type.equalsIgnoreCase("offMarket"))
      {
         field = "displayStatus";
      }

      final List<PropertyUpdateRecordModel> propertyUpdateRecords = propertyUpdateRecordRepository.findByCreationTimeGreaterThanAndFieldAndPropertyChannel(reportStartDate, field, channel.getCode());
      LOG.info("Number of properties let [{}] ", propertyUpdateRecords.size());
      return propertyUpdateRecords.size();
   }
}
