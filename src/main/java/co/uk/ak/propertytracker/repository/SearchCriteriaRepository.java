package co.uk.ak.propertytracker.repository;

import co.uk.ak.propertytracker.model.SearchCriteriaModel;
import org.springframework.data.repository.CrudRepository;

public interface SearchCriteriaRepository extends CrudRepository<SearchCriteriaModel, Long>
{
}
