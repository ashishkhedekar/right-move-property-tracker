package co.uk.ak.propertytracker.service.impl;

import co.uk.ak.propertytracker.dto.LettingPropertiesTrackingResult;
import co.uk.ak.propertytracker.mapper.RightMovePropertyToPropertModelMapper;
import co.uk.ak.propertytracker.repository.PropertyRepository;
import co.uk.ak.propertytracker.rightmove.dto.RightMoveResult;
import co.uk.ak.propertytracker.model.PropertyModel;
import co.uk.ak.propertytracker.rightmove.client.RightMoveWebClient;
import co.uk.ak.propertytracker.rightmove.dto.RightMoveProperty;
import co.uk.ak.propertytracker.service.RightMoveTrackingService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultRightMoveTrackingService implements RightMoveTrackingService
{
   private static final Logger LOG = LoggerFactory.getLogger(DefaultRightMoveTrackingService.class);

   @Autowired
   private RightMoveWebClient rightMoveWebClient;
   @Autowired
   private RightMovePropertyToPropertModelMapper rightMovePropertyToPropertModelMapper;
   @Autowired
   private PropertyRepository propertyRepository;

   @Value("${right.move.base.url}")
   private String rightMoveBaseUrl;

   @Override
   public LettingPropertiesTrackingResult trackProperties(final RightMoveResult rightMoveResult)
   {
      final AtomicInteger numberOfPropertiesLet = new AtomicInteger();
      final AtomicInteger newProperties = new AtomicInteger();
      final List<RightMoveProperty> letProperties = new ArrayList<>();

      rightMoveResult.getProperties().forEach(property -> {
         LOG.info("Processing property [{}]", property.getId());
         final Optional<PropertyModel> rightMovePropertyOptional = propertyRepository.findById(Long.valueOf(property.getId()));
         rightMovePropertyOptional.map(propertyModel -> {
            LOG.debug("Property [{}] found and going to udpate ", property.getId());
            if (!StringUtils.equalsIgnoreCase(property.getDisplayStatus(), propertyModel.getDisplayStatus()) && property.getDisplayStatus().equalsIgnoreCase("Let agreed"))
            {
               LOG.debug("The property's stauts changed from [{}] to [{}] ", propertyModel.getDisplayStatus(), property.getDisplayStatus());
               numberOfPropertiesLet.getAndIncrement();
               property.setFullPropertyUrl(rightMoveBaseUrl + property.getPropertyUrl());
               //todo revisit this
               if (propertyAlreadyAdded(letProperties, property))
               {
                  LOG.info("Property [{}] has already been added so going to ignore it", property.getId());
               }
               else
               {
                  LOG.info("New property [{}] let, going to add to the let", property.getId());
                  letProperties.add(property);
               }
               final Period period = Period.between(propertyModel.getFirstVisibleDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now());
               property.setDaysOnMarket(period.getDays());
            }
            return propertyModel;
         }).orElseGet(() -> {
            newProperties.getAndIncrement();
            return null;
         });
      });

      return LettingPropertiesTrackingResult.builder()
               .numberOfPropertiesLet(numberOfPropertiesLet.get())
               .newProperties(newProperties.get())
               .letProperties(letProperties)
               .build();
   }

   private boolean propertyAlreadyAdded(final List<RightMoveProperty> letProperties, final RightMoveProperty rightMoveProperty)
   {
      return letProperties.stream()
               .anyMatch(e -> e.getId().equals(rightMoveProperty.getId()));
   }

   @Override
   public void refreshProperties(final RightMoveResult rightMoveResult)
   {
      rightMoveResult.getProperties().forEach(property -> {
         final Optional<PropertyModel> rightMovePropertyOptional = propertyRepository.findById(Long.valueOf(property.getId()));
         final PropertyModel propertyModel;
         if (rightMovePropertyOptional.isPresent())
         {
            propertyModel = rightMovePropertyOptional.get();
            LOG.info("Property with id [{}] already exists, updating the status", propertyModel.getId());
            propertyModel.setDisplayStatus(property.getDisplayStatus());
         }
         else
         {
            //todo update number of days on the market
            LOG.info("New property with id [{}] recently added, adding it to db", property.getId());
            propertyModel = rightMovePropertyToPropertModelMapper.rightMovePropertyToPropertyModel(property);
            LOG.info("The converted property model is [{}]", propertyModel);
         }
         propertyRepository.save(propertyModel);
         LOG.info("Property with id [{}] was successfully saved in db ", propertyModel.getId());
      });
   }
}
