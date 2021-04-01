package co.uk.ak.propertytracker.facade.impl;

import co.uk.ak.propertytracker.facade.PropertyFacade;
import co.uk.ak.propertytracker.repository.PropertyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DefaultPropertyFacade implements PropertyFacade {

	PropertyRepository propertyRepository;

	@Override
	public long countAll() {
		return propertyRepository.count();
	}
}
