package co.uk.ak.propertytracker.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Data
@Builder
public class MarketMovementReport
{
   private int numberOfNewProperties;

   private List<PropertyDto> newProperties;

   private int numberOfOffMarketProperties;

   private List<PropertyDto> offMarketProperties;

   private Channel channel;

   public boolean needsReporting()
   {
      return !CollectionUtils.isEmpty(newProperties) || !CollectionUtils.isEmpty(offMarketProperties);
   }
}
