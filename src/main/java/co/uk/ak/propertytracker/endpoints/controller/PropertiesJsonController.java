package co.uk.ak.propertytracker.endpoints.controller;

import co.uk.ak.propertytracker.dto.Channel;
import co.uk.ak.propertytracker.dto.LocationMarketMovementReport;
import co.uk.ak.propertytracker.endpoints.dtos.PropertyWsDto;
import co.uk.ak.propertytracker.facade.LocationFacade;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
@AllArgsConstructor
public class PropertiesJsonController
{
   private static final Logger LOG = LoggerFactory.getLogger(PropertiesJsonController.class);

   private final LocationFacade locationFacade;

   @GetMapping("/market-summary")
   public ResponseEntity<List<LocationMarketMovementReport>> summary(@RequestParam(defaultValue = "21") int numberOfDays)
   {
      final Date reportStartDate = DateTime.now().minusDays(numberOfDays).toDate();
      final List<LocationMarketMovementReport> locationMarketMovementReports = new ArrayList<>();
      locationFacade.getAllLocations().forEach(locationDto -> {
         LOG.info("Getting data for Location id [{}] and identifier [{}] ", locationDto.getId(), locationDto.getLocationIdentifier());
         final Set<PropertyWsDto> locationWsDtos = locationFacade.findRecentlyOffMarketProperties(locationDto.getId(), reportStartDate);

         final long numberOfLetProperties = locationWsDtos.stream()
                 .filter(locationWsDto -> locationWsDto.getChannel() == Channel.RENT)
                 .count();

         final long numberOfSoldProperties = locationWsDtos.stream()
                 .filter(locationWsDto -> locationWsDto.getChannel() == Channel.BUY)
                 .count();

         final LocationMarketMovementReport locationMarketMovementReport = LocationMarketMovementReport.builder()
                          .locationId(locationDto.getId())
                          .numberOfLetProperties(numberOfLetProperties)
                          .numberOfSoldProperties(numberOfSoldProperties)
                          .locationName(locationDto.getDescription())
                          .description(locationDto.getDescription())
                          .build();
         locationMarketMovementReports.add(locationMarketMovementReport);
      });
      return ResponseEntity.status(HttpStatus.OK).body(locationMarketMovementReports);
   }

   @GetMapping("/market-details")
   public ResponseEntity<Set<PropertyWsDto>> getMarketDetails(@RequestParam(required = false) Long locationId, @RequestParam(defaultValue = "21") int numberOfDays) {
      final Date cutOffDate = DateTime.now().minusDays(numberOfDays).toDate();
      final Set<PropertyWsDto> locationModels = locationFacade.findRecentlyOffMarketProperties(locationId, cutOffDate);
      return ResponseEntity.status(HttpStatus.OK).body(locationModels);
   }

//   @CrossOrigin(origins = "http://localhost:4200")
//   @GetMapping("/properties-stats")
//   public ResponseEntity<List<LocationMarketMovementReport>> stats(@RequestParam(defaultValue = "7") int numberOfDays, @RequestParam(required = false) Channel channel, @RequestParam(required = false) String type)
//   {
//      final Date reportStartDate = DateTime.now().minusDays(numberOfDays).toDate();
//      final Map<String, Set<PropertyUpdateRecordDto>> stats = propertyUpdateRecordFacade.getStats(reportStartDate, channel, type);
//
//      List<LocationMarketMovementReport> locationMarketMovementReports = new ArrayList<>();
//
//      stats.keySet().forEach(e -> {
//         final Set<PropertyUpdateRecordDto> propertyUpdateRecordDtos = stats.get(e);
//
//         final List<PropertyDto> letProperties = propertyUpdateRecordDtos.stream()
//                  .filter(u -> u.getField().equalsIgnoreCase("displayStatus") && u.getNewValue().equalsIgnoreCase("Let Agreed"))
//                  .map(u -> propertyDao.findProperty(u.getPropertyId()))
//                  .collect(Collectors.toList());
//
//         final List<PropertyDto> soldProperties = propertyUpdateRecordDtos.stream()
//                  .filter(u -> u.getField().equalsIgnoreCase("displayStatus") && u.getNewValue().equalsIgnoreCase("Sold STC"))
//                  .map(u -> propertyDao.findProperty(u.getPropertyId()))
//                  .collect(Collectors.toList());
//
//         final List<PropertyDto> newProperties = propertyUpdateRecordDtos.stream()
//                  .filter(u -> u.getField().equalsIgnoreCase("registered") && u.getNewValue().equalsIgnoreCase("true"))
//                  .map(u -> propertyDao.findProperty(u.getPropertyId()))
//                  .collect(Collectors.toList());
//
//         final Optional<LocationModel> location = locationDao.findLocationForLocationIdentifier(e);
//
//         final LocationMarketMovementReport locationMarketMovementReport = LocationMarketMovementReport.builder()
//                  .locationId(e)
//                  .letProperties(letProperties)
//                  .numberOfLetProperties(letProperties.size())
//                  .soldProperties(soldProperties)
//                  .numberOfSoldProperties(soldProperties.size())
//                  .newProperties(newProperties)
//                  .numberOfNewProperties(newProperties.size())
//                  .locationName(location.isPresent() ? location.get().getDescription() : "UNKNOWN")
//                  .build();
//         locationMarketMovementReports.add(locationMarketMovementReport);
//
//      });
//      return ResponseEntity.status(HttpStatus.OK).body(locationMarketMovementReports);
//   }

}
