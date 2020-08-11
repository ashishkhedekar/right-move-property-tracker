package co.uk.ak.propertytracker.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sales_search_criteria")
public class SalesSearchCriteriaModel extends SearchCriteriaModel
{
   private boolean includeSSTC;

   public boolean isIncludeSSTC()
   {
      return includeSSTC;
   }

   public void setIncludeSSTC(boolean includeSSTC)
   {
      this.includeSSTC = includeSSTC;
   }
}
