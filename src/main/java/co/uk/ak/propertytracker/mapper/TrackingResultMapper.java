package co.uk.ak.propertytracker.mapper;

import co.uk.ak.propertytracker.dto.MarketMovementReport;
import co.uk.ak.propertytracker.model.TrackingResultModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = RightMovePropertyToPropertModelMapper.class)
public interface TrackingResultMapper
{
   @Mapping(target = "createdDate", expression = "java(org.joda.time.DateTime.now().toDate())")
   TrackingResultModel trackingResultToModel(MarketMovementReport trackingResult);
}
