package co.uk.ak.rightmove.propertytracker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "properties")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RightMovePropertyModel
{
   @Id
   private Long id;

   private Integer bedrooms;
   @Lob
   private String summary;
   private String displayAddress;
   private String propertySubType;
   private String displayStatus;
   private Date firstVisibleDate;
   private long daysOnMarket;




////   @OneToOne
//   private RightMovePriceModel price;
//   private String propertyUrl;
//   private String firstVisibleDate;

//   private String addedOrReduced;
//   private Boolean isRecent;
//   private String formattedDistance;
}
