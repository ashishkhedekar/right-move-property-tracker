package co.uk.ak.propertytracker.model;

import javax.persistence.*;

@Entity
@Table(name = "right_move_search_results")
@Inheritance(strategy = InheritanceType.JOINED)
public class RightMoveSearchResultModel extends AbstractModel
{
   @Lob
   private String searchResult;

   @ManyToOne(fetch = FetchType.LAZY)
   private SearchCriteriaModel searchCriteria;

   public String getSearchResult()
   {
      return searchResult;
   }

   public void setSearchResult(String searchResult)
   {
      this.searchResult = searchResult;
   }

   public SearchCriteriaModel getSearchCriteria()
   {
      return searchCriteria;
   }

   public void setSearchCriteria(SearchCriteriaModel searchCriteria)
   {
      this.searchCriteria = searchCriteria;
   }
}
