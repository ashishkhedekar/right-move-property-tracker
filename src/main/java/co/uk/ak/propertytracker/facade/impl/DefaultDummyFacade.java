package co.uk.ak.propertytracker.facade.impl;

import co.uk.ak.propertytracker.facade.DummyFacade;
import co.uk.ak.propertytracker.repository.RightMovePropertyRepository;
import co.uk.ak.propertytracker.model.RightMovePropertyModel;
import co.uk.ak.propertytracker.model.TrackingResultModel;
import co.uk.ak.propertytracker.repository.TrackingResultRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class DefaultDummyFacade implements DummyFacade
{
   private final TrackingResultRepository trackingResultRepository;

   private final RightMovePropertyRepository propertyRepository;

   @Override
   public long howManyProperties()
   {
      final Iterable<RightMovePropertyModel> all = propertyRepository.findAll();
      return StreamSupport.stream(all.spliterator(), false).count();
   }

   @Override
   public long howManyResults()
   {
      final Iterable<TrackingResultModel> all = trackingResultRepository.findAll();
      return StreamSupport.stream(all.spliterator(), false).count();
   }

}
