package co.uk.ak.propertytracker.endpoints.controller;

import co.uk.ak.propertytracker.dto.Channel;
import co.uk.ak.propertytracker.facade.PropertyUpdateRecordFacade;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
@AllArgsConstructor
public class PropertiesJsonController
{
   private final PropertyUpdateRecordFacade propertyUpdateRecordFacade;

   @GetMapping("/properties-stats")
   public ResponseEntity<Integer> stats(@RequestParam int numberOfDays, @RequestParam Channel channel, @RequestParam String type)
   {
      final Date reportStartDate = DateTime.now().minusDays(numberOfDays).toDate();
      final int stats = propertyUpdateRecordFacade.getStats(reportStartDate, channel, type);
      return ResponseEntity.status(HttpStatus.OK).body(stats);
   }

}
