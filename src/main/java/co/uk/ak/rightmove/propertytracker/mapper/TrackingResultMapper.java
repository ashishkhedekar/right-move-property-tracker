package co.uk.ak.rightmove.propertytracker.mapper;

import co.uk.ak.rightmove.propertytracker.dto.LettingPropertiesTrackingResult;
import co.uk.ak.rightmove.propertytracker.model.TrackingResultModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TrackingResultMapper
{
   @Mapping(target = "createdDate", expression = "java(org.joda.time.DateTime.now().toDate())")
   TrackingResultModel trackingResultToModel(LettingPropertiesTrackingResult trackingResult);
}
