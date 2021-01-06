package co.uk.ak.propertytracker.mapper;

import co.uk.ak.propertytracker.endpoints.dtos.PropertyWsDto;
import co.uk.ak.propertytracker.model.PropertyModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.temporal.ChronoUnit;

@Mapper(componentModel = "spring")
public interface PropertyModelToPropertyWsDtoMapper {

	@Mapping(target = "firstVisibleDate", source = "firstVisibleDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
	@Mapping(target = "offMarketDate", source = "offMarketDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
	@Mapping(target = "daysOnMarket", expression = "java(daysOnMarket(propertyModel))")
	PropertyWsDto propertyModelPropertyWsDtoMapper(final PropertyModel propertyModel);

	default Long daysOnMarket(PropertyModel propertyModel)
	{
		return ChronoUnit.DAYS
				.between(propertyModel.getFirstVisibleDate().toInstant(), propertyModel.getOffMarketDate().toInstant());
	}
}
