package co.uk.ak.rightmove.propertytracker.model;

import co.uk.ak.rightmove.propertytracker.dto.DisplayPrice;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
