package co.uk.ak.rightmove.propertytracker.service.impl;

import co.uk.ak.rightmove.propertytracker.dto.LettingPropertiesTrackingResult;
import co.uk.ak.rightmove.propertytracker.dto.RightMoveResult;
import co.uk.ak.rightmove.propertytracker.mapper.RightMovePropertyMapper;
import co.uk.ak.rightmove.propertytracker.model.RightMovePropertyModel;
import co.uk.ak.rightmove.propertytracker.repository.RightMovePropertyRepository;
import co.uk.ak.rightmove.propertytracker.rightmove.client.RightMoveWebClient;
import co.uk.ak.rightmove.propertytracker.rightmove.dto.Property;
import co.uk.ak.rightmove.propertytracker.service.RightMoveTrackingService;
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
   private RightMovePropertyMapper rightMovePropertyMapper;
   @Autowired
   private RightMovePropertyRepository rightMovePropertyRepository;

   @Value("${right.move.base.url}")
   private String rightMoveBaseUrl;

   @Override
   public LettingPropertiesTrackingResult trackProperties(final RightMoveResult rightMoveResult)
   {
      final AtomicInteger numberOfPropertiesLet = new AtomicInteger();
      final AtomicInteger newProperties = new AtomicInteger();
      final List<Property> letProperties = new ArrayList<>();

      rightMoveResult.getProperties().forEach(property -> {
         LOG.info("Processing property [{}]", property.getId());
         final Optional<RightMovePropertyModel> rightMovePropertyOptional = rightMovePropertyRepository.findById(Long.valueOf(property.getId()));
         rightMovePropertyOptional.map(propertyModel -> {
            LOG.info("Property [{}] found and going to udpate ", property.getId());
            if (!StringUtils.equalsIgnoreCase(property.getDisplayStatus(), propertyModel.getDisplayStatus()) && property.getDisplayStatus().equalsIgnoreCase("Let agreed"))
            {
               LOG.info("The property's stauts changed from [{}] to [{}] ", propertyModel.getDisplayStatus(), property.getDisplayStatus());
               numberOfPropertiesLet.getAndIncrement();
               property.setFullPropertyUrl(rightMoveBaseUrl + property.getPropertyUrl());
               letProperties.add(property);
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
            //todo update number of days on the market
            LOG.info("New property with id [{}] recently added, adding it to db", property.getId());
            rightMovePropertyModel = rightMovePropertyMapper.propertyToPropertyModel(property);
            LOG.info("The converted property model is [{}]", rightMovePropertyModel);
         }
         rightMovePropertyRepository.save(rightMovePropertyModel);
         LOG.info("Property with id [{}] was successfully saved in db ", rightMovePropertyModel.getId());
      });
   }
}
