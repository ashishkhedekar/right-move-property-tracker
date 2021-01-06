package co.uk.ak.propertytracker.dto;

public enum Channel
{
   BUY("BUY"), RENT("RENT"), COMMERCIAL_BUY("COMMERCIAL_BUY");

   private final String code;

   Channel(String code)
   {
      this.code = code;
   }

   public String getCode()
   {
      return code;
   }
}
