package co.uk.ak.propertytracker.dto;

public enum Channel
{
   BUY("BUY"), RENT("RENT");

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
