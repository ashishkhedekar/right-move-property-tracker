package co.uk.ak.propertytracker.repository;

import co.uk.ak.propertytracker.model.SearchCriteriaModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SearchCriteriaRepository extends CrudRepository<SearchCriteriaModel, Long>
{
   Optional<SearchCriteriaModel> findByMinBedroomsAndMaxPriceAndChannelAndLocationLocationIdentifier(int minBedrooms, long maxPrice, String channel, String locationIdentifier);
}
