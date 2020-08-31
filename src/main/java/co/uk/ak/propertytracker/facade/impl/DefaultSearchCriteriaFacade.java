package co.uk.ak.propertytracker.facade.impl;

import co.uk.ak.propertytracker.dto.LocationDto;
import co.uk.ak.propertytracker.endpoints.searchcriteriadto.SearchCriteriaDto;
import co.uk.ak.propertytracker.facade.SearchCriteriaFacade;
import co.uk.ak.propertytracker.mapper.SearchCriteriaMapper;
import co.uk.ak.propertytracker.model.LettingsSearchCriteriaModel;
import co.uk.ak.propertytracker.model.LocationModel;
import co.uk.ak.propertytracker.model.SalesSearchCriteriaModel;
import co.uk.ak.propertytracker.model.SearchCriteriaModel;
import co.uk.ak.propertytracker.repository.SearchCriteriaRepository;
import co.uk.ak.propertytracker.service.LocationDao;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DefaultSearchCriteriaFacade implements SearchCriteriaFacade
{
   private static final Logger LOG = LoggerFactory.getLogger(DefaultSearchCriteriaFacade.class);

   private final SearchCriteriaMapper searchCriteriaMapper;
   private final SearchCriteriaRepository searchCriteriaRepository;
   private final LocationDao locationDao;

   @Override
   public void save(final SearchCriteriaDto searchCriteriaDto)
   {
      //check if search criteria already exists
      final Optional<SearchCriteriaModel> existingSearchCriteriaOptional = searchCriteriaRepository.findByMinBedroomsAndMaxPriceAndChannelAndLocationLocationIdentifier(
               searchCriteriaDto.getMinBedrooms(),
               searchCriteriaDto.getMaxPrice(),
               searchCriteriaDto.getChannel().getCode(),
               searchCriteriaDto.getLocationIdentifier());

      if (existingSearchCriteriaOptional.isEmpty())
      {
         //Insert if does not exist
         final SearchCriteriaModel searchCriteriaModel = searchCriteriaMapper.toSearchCriteriaModel(searchCriteriaDto);
         searchCriteriaModel.setLocation(getOrCreateLocation(searchCriteriaDto));
         searchCriteriaRepository.save(searchCriteriaModel);
      }
      else
      {
         LOG.warn("Search Criteria with Location Identifier [{}] already exists ", searchCriteriaDto.getLocationIdentifier());
         final SearchCriteriaModel searchCriteriaModel = existingSearchCriteriaOptional.get();
         searchCriteriaModel.setLocation(getOrCreateLocation(searchCriteriaDto));
         //Populate other fields for update operation if necessary
         searchCriteriaModel.setAreaSizeUnit(searchCriteriaDto.getAreaSizeUnit());
      }
   }

   private LocationModel getOrCreateLocation(SearchCriteriaDto searchCriteriaDto)
   {
      final LocationDto locationDto = LocationDto.builder()
               .locationIdentifier(searchCriteriaDto.getLocationIdentifier())
               .build();
      return locationDao.getOrCreate(locationDto);
   }

   @Override
   public List<SearchCriteriaDto> getAll()
   {
      final List<SearchCriteriaDto> searchCriteriaDtos = new ArrayList<>();
      searchCriteriaRepository.findAll()
               .forEach(e -> {
                  LOG.info("The type of object received has channel [{}] and class is [{}] ", e.getChannel(), e.getClass().getName());
                  if (e instanceof LettingsSearchCriteriaModel)
                  {
                     searchCriteriaDtos.add(searchCriteriaMapper.lettingsSearchCriteriaModelToSearchCriteriaDto((LettingsSearchCriteriaModel) e));
                  }
                  else
                  {
                     searchCriteriaDtos.add(searchCriteriaMapper.salesSearchCriteriaModelToSearchCriteriaDto((SalesSearchCriteriaModel) e));
                  }
               });
      return searchCriteriaDtos;
   }

   @Override
   public Optional<SearchCriteriaDto> findByLocationIdentifier()
   {
      return Optional.empty();
   }
}
