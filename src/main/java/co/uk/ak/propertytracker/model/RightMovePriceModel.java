package co.uk.ak.propertytracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RightMovePriceModel
{
//   @Id
   private Long id;

   private Integer amount;
   private String frequency;
   private String currencyCode;

//   @OneToMany
   private List<RightMoveDisplayPriceModel> displayPrices;
}
