package co.uk.ak.propertytracker.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MarketMovementReport
{
   private int numberOfNewProperties;

   private List<PropertyDto> newProperties;

   private int numberOfOffMarketProperties;

   private List<PropertyDto> offMarketProperties;

   private String channel;

   public boolean needsReporting()
   {
      return getNewProperties().size() > 0 || getOffMarketProperties().size() > 0;
   }
}
