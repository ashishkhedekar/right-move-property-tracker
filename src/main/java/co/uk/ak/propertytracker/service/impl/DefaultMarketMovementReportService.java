package co.uk.ak.propertytracker.service.impl;

import co.uk.ak.propertytracker.dto.MarketMovementReport;
import co.uk.ak.propertytracker.dto.PropertyDto;
import co.uk.ak.propertytracker.mapper.PropertyDtoToPropertyModelMapper;
import co.uk.ak.propertytracker.model.PropertyModel;
import co.uk.ak.propertytracker.model.PropertyUpdateRecordModel;
import co.uk.ak.propertytracker.repository.PropertyRepository;
import co.uk.ak.propertytracker.repository.PropertyUpdateRecordRepository;
import co.uk.ak.propertytracker.service.MarketMovementReportService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DefaultMarketMovementReportService implements MarketMovementReportService
{
   private static final Logger LOG = LoggerFactory.getLogger(DefaultMarketMovementReportService.class);

   private final PropertyUpdateRecordRepository propertyUpdateRecordRepository;
   private final PropertyDtoToPropertyModelMapper propertyDtoToPropertyModelMapper;
   private final PropertyRepository propertyRepository;

   @Override
   public MarketMovementReport generateMarketMovementReport(final Date reportStartTime, final String channel)
   {
      //Generate Reports
      final AtomicInteger numberOfLetProperties = new AtomicInteger();
      final List<PropertyDto> letProperties = new ArrayList<>();

      // Get Property Updates based on the channel
      final List<PropertyUpdateRecordModel> propertyUpdates = propertyUpdateRecordRepository.findByCreationTimeGreaterThanAndPropertyChannel(reportStartTime, channel);
      LOG.info("update records found [{}]", propertyUpdates.size());
      propertyUpdates.stream()
               .filter(p -> p.getField().equalsIgnoreCase("displayStatus"))
               .forEach(p -> {
                  numberOfLetProperties.incrementAndGet();
                  letProperties.add(propertyDtoToPropertyModelMapper.propertyModelPropertyDtoMapper(p.getProperty()));
               });

      final List<PropertyModel> newPropertiesOnTheMarket = propertyRepository.findByCreationTimeGreaterThan(reportStartTime);
      LOG.info("Number of new properties found [{}]", newPropertiesOnTheMarket.size());
      final List<PropertyDto> newPropertiesDtoOnTheMarket = newPropertiesOnTheMarket.stream()
               .map(propertyDtoToPropertyModelMapper::propertyModelPropertyDtoMapper)
               .collect(Collectors.toList());

      //Send Emails
      return MarketMovementReport.builder()
               .numberOfOffMarketProperties(numberOfLetProperties.get())
               .offMarketProperties(letProperties)
               .numberOfNewProperties(newPropertiesDtoOnTheMarket.size())
               .newProperties(newPropertiesDtoOnTheMarket)
               .channel(channel)
               .build();
   }
}