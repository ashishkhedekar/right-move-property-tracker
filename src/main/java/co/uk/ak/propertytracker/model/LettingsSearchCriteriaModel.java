package co.uk.ak.propertytracker.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "lettings_search_criteria")
public class LettingsSearchCriteriaModel extends SearchCriteriaModel
{
   private boolean includeLetAgreed;

   public boolean isIncludeLetAgreed()
   {
      return includeLetAgreed;
   }

   public void setIncludeLetAgreed(boolean includeLetAgreed)
   {
      this.includeLetAgreed = includeLetAgreed;
   }
}
