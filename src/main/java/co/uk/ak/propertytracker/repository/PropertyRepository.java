package co.uk.ak.propertytracker.repository;

import co.uk.ak.propertytracker.model.PropertyModel;
import org.springframework.data.repository.CrudRepository;

public interface PropertyRepository extends CrudRepository<PropertyModel, Long>
{

}
