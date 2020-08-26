package co.uk.ak.propertytracker.service.impl;

import co.uk.ak.propertytracker.dto.MarketMovementReport;
import co.uk.ak.propertytracker.dto.PropertyDto;
import co.uk.ak.propertytracker.mapper.PropertyDtoToPropertyModelMapper;
import co.uk.ak.propertytracker.model.PropertyModel;
import co.uk.ak.propertytracker.repository.PropertyRepository;
import co.uk.ak.propertytracker.service.PropertyOffMarketReportService;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class DefaultPropertyOffMarketReportService implements PropertyOffMarketReportService
{
   private final PropertyRepository propertyRepository;
   private final PropertyDtoToPropertyModelMapper propertyDtoToPropertyModelMapper;

   @Override
   @Transactional
   public MarketMovementReport generatePropertyOffMarketReport()
   {
      final Date cutOffDate = DateTime.now().withTimeAtStartOfDay().toDate();
      final List<PropertyModel> offMarketProperties = propertyRepository.findByLastPropertyUpdateReceivedLessThanOrLastPropertyUpdateReceivedIsNull(cutOffDate);

      final List<PropertyDto> offMarketPropertiesDtos = new ArrayList<>();

      offMarketProperties.stream()
               .filter(e -> e.isOnMarket() == null || e.isOnMarket())
               .forEach(propertyModel -> {
                  propertyModel.setOnMarket(false);
                  propertyModel.setOffMarketDate(cutOffDate);
                  propertyRepository.save(propertyModel);
                  final PropertyDto offMarketPropertyDto = propertyDtoToPropertyModelMapper.propertyModelPropertyDtoMapper(propertyModel);
                  offMarketPropertiesDtos.add(offMarketPropertyDto);
               });

      return MarketMovementReport.builder()
               .offMarketProperties(offMarketPropertiesDtos)
               .numberOfOffMarketProperties(offMarketPropertiesDtos.size())
               .build();
   }
}
