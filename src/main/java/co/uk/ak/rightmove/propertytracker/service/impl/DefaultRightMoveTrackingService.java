package co.uk.ak.rightmove.propertytracker.service.impl;

import co.uk.ak.rightmove.propertytracker.client.RightMoveWebClient;
import co.uk.ak.rightmove.propertytracker.dto.RightMoveResult;
import co.uk.ak.rightmove.propertytracker.dto.TrackingResult;
import co.uk.ak.rightmove.propertytracker.mapper.RightMovePropertyMapper;
import co.uk.ak.rightmove.propertytracker.model.RightMovePropertyModel;
import co.uk.ak.rightmove.propertytracker.repository.RightMovePropertyRepository;
import co.uk.ak.rightmove.propertytracker.service.RightMoveTrackingService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Data
@AllArgsConstructor
public class DefaultRightMoveTrackingService implements RightMoveTrackingService
{
   private static final Logger LOG = LoggerFactory.getLogger(DefaultRightMoveTrackingService.class);

   private final RightMoveWebClient rightMoveWebClient;
   private final RightMovePropertyMapper rightMovePropertyMapper;
   private final RightMovePropertyRepository rightMovePropertyRepository;

   @Override
   public TrackingResult trackProperties(final RightMoveResult rightMoveResult)
   {
      final AtomicInteger numberOfPropertiesLet = new AtomicInteger();
      final AtomicInteger newProperties = new AtomicInteger();
      rightMoveResult.getProperties().forEach(property -> {
         final Optional<RightMovePropertyModel> rightMovePropertyOptional = rightMovePropertyRepository.findById(Long.valueOf(property.getId()));
         rightMovePropertyOptional.map(p -> {
            if (!StringUtils.equalsIgnoreCase(property.getDisplayStatus(), p.getDisplayStatus()) && property.getDisplayStatus().equalsIgnoreCase("Let agreed"))
            {
               numberOfPropertiesLet.getAndIncrement();
            }
            return p;
         }).orElseGet(() -> {
            newProperties.getAndIncrement();
            return null;
         });
      });

      return TrackingResult.builder()
               .numberOfPropertiesLet(numberOfPropertiesLet.get())
               .newProperties(newProperties.get())
               .build();
   }

   @Override
   public void refreshProperties(final RightMoveResult rightMoveResult)
   {
      rightMoveResult.getProperties().forEach(property -> {
         final Optional<RightMovePropertyModel> rightMovePropertyOptional = rightMovePropertyRepository.findById(Long.valueOf(property.getId()));
         final RightMovePropertyModel rightMovePropertyModel;
         if (rightMovePropertyOptional.isPresent())
         {
            rightMovePropertyModel = rightMovePropertyOptional.get();
            LOG.info("Property with id [{}] already exists, updating the status", rightMovePropertyModel.getId());
            rightMovePropertyModel.setDisplayStatus(property.getDisplayStatus());
         }
         else
         {
            LOG.info("New property with id [{}] recently added, ading it to db", property.getId());
            rightMovePropertyModel = rightMovePropertyMapper.propertyToPropertyModel(property);
            LOG.info("The converted property model is [{}]", rightMovePropertyModel);
         }
         rightMovePropertyRepository.save(rightMovePropertyModel);
         LOG.info("Property with id [{}] was successfully saved in db ", rightMovePropertyModel.getId());
      });
   }
}
