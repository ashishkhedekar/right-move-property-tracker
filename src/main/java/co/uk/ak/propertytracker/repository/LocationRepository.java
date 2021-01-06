package co.uk.ak.propertytracker.repository;

import co.uk.ak.propertytracker.model.LocationModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends CrudRepository<LocationModel, Long>
{
   Optional<LocationModel> findByLocationIdentifier(String locationIdentifier);

   Optional<LocationModel> findById(String locationIdentifier);

   List<LocationModel> findByProperties_PropertyId(Long propertyId);
}
