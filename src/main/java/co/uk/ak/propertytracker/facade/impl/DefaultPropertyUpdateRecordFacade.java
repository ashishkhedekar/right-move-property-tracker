package co.uk.ak.propertytracker.facade.impl;

import co.uk.ak.propertytracker.dto.Channel;
import co.uk.ak.propertytracker.dto.PropertyUpdateRecordDto;
import co.uk.ak.propertytracker.facade.PropertyUpdateRecordFacade;
import co.uk.ak.propertytracker.mapper.PropertyUpdateRecordModelMapper;
import co.uk.ak.propertytracker.model.PropertyUpdateRecordModel;
import co.uk.ak.propertytracker.repository.LocationRepository;
import co.uk.ak.propertytracker.repository.PropertyUpdateRecordRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

@Service
@AllArgsConstructor
public class DefaultPropertyUpdateRecordFacade implements PropertyUpdateRecordFacade
{
   private static final Logger LOG = LoggerFactory.getLogger(DefaultPropertyUpdateRecordFacade.class);

   private final PropertyUpdateRecordModelMapper propertyUpdateRecordModelMapper;
   private final PropertyUpdateRecordRepository propertyUpdateRecordRepository;
   private final LocationRepository locationRepository;

   @Override
   public Map<String, Set<PropertyUpdateRecordDto>> getStats(final Date reportStartDate, final Channel channel, final String type)
   {
      String field = null;
      if (type != null && type.equalsIgnoreCase("offMarket"))
      {
         field = "displayStatus";
      }

      final List<PropertyUpdateRecordModel> propertyUpdateRecords = propertyUpdateRecordRepository.findByCreationTimeGreaterThanAndFieldAndPropertyChannel(reportStartDate, field, channel.getCode());

      return propertyUpdateRecords.stream()
               .map(propertyUpdateRecordModelMapper::propertyUpdateRecordModelToDto)
               .collect(groupingBy(PropertyUpdateRecordDto::getLocationIdentifier, toSet()));

   }
}
