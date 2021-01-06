package co.uk.ak.propertytracker.endpoints.dtos;

import co.uk.ak.propertytracker.dto.Channel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PropertyWsDto {

	private Long propertyId;
	private Integer bedrooms;
	private String summary;
	private String displayAddress;
	private String displayPrice;
	private Channel channel;
	private String firstVisibleDate;
	private String offMarketDate;
	private String mainMapImageSrc;
	private Long daysOnMarket;
}
