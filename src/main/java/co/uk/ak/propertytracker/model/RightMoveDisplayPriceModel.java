package co.uk.ak.propertytracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RightMoveDisplayPriceModel
{
//   @Id
   private Long id;

   private String displayPrice;
   private String displayPriceQualifier;

//   @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   private RightMovePriceModel price;
}
