package co.uk.ak.rightmove.propertytracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.EnableLoadTimeWeaving;

import javax.persistence.*;

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
