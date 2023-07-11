package it.unisalento.iot.gateway.domains;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;
import java.util.Date;

@Document("aggregatedData")
@Getter
@Setter
@NoArgsConstructor
public class AggregatedData implements Serializable {

  @Id private String id;
  @CreatedDate private Date date;
//  @Value("${boiler.id}")
  private String boilerId;
  private float temperatureAverageData;
  private float pressureAverageData;
  private float carbonMonoxideAverageData;
  private float performanceAverageData;
}
