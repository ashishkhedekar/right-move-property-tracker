package co.uk.ak.propertytracker.service.impl;

import co.uk.ak.propertytracker.model.RightMoveSearchResultModel;
import co.uk.ak.propertytracker.model.SearchCriteriaModel;
import co.uk.ak.propertytracker.repository.RightMoveSearchResultRepository;
import co.uk.ak.propertytracker.repository.SearchCriteriaRepository;
import co.uk.ak.propertytracker.service.RightMoveSearchResultDao;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DefaultRightMoveSearchResultDao implements RightMoveSearchResultDao
{
   private static final Logger LOG = LoggerFactory.getLogger(DefaultRightMoveSearchResultDao.class);

   private final RightMoveSearchResultRepository rightMoveSearchResultRepository;
   private final SearchCriteriaRepository searchCriteriaRepository;

   @Override
   public void save(final String rightMoveSearchResult, final Long searchCriteriaId)
   {
      final Optional<SearchCriteriaModel> searchCriteriaModel = searchCriteriaRepository.findById(searchCriteriaId);
      if (searchCriteriaModel.isPresent())
      {
         LOG.info("Found search criteria with id [{}], saving the  RightMoveSearchResult", searchCriteriaModel.get().getId());
         final RightMoveSearchResultModel rightMoveSearchResultModel = new RightMoveSearchResultModel();
         rightMoveSearchResultModel.setSearchCriteria(searchCriteriaModel.get());
         rightMoveSearchResultModel.setSearchResult(rightMoveSearchResult);
         rightMoveSearchResultRepository.save(rightMoveSearchResultModel);
      }
      else
      {
         LOG.info("Search criteria with id [{}] NOT FOUND.", searchCriteriaId);
      }
   }
}
