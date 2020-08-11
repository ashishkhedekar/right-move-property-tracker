package co.uk.ak.propertytracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tracking_results")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrackingResultModel
{
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

   private int numberOfPropertiesLet;
   private int numberOfPropertiesReduced;
   private int numberOfPropertiesSold;
   private Date createdDate;

   @OneToMany(fetch = FetchType.LAZY)
   private List<RightMovePropertyModel> letProperties;

   @OneToMany(fetch = FetchType.LAZY)
   private List<RightMovePropertyModel> reducedProperties;

   @OneToMany(fetch = FetchType.LAZY)
   private List<RightMovePropertyModel> soldProperties;
}
