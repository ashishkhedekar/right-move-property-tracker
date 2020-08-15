package co.uk.ak.propertytracker.repository;

import co.uk.ak.propertytracker.model.PropertyUpdateRecordModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface PropertyUpdateRecordRepository extends CrudRepository<PropertyUpdateRecordModel, Long>
{
   //todo: get based on the channel
   List<PropertyUpdateRecordModel> findByCreationTimeGreaterThan(Date referenceDate);
}
