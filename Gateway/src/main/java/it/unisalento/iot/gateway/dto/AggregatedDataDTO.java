package it.unisalento.iot.gateway.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class AggregatedDataDTO {

  @Id private String id;
  @CreatedDate private Date date;
  private String boilerId;
  private float temperatureAverageData;
  private float pressureAverageData;
  private float carbonMonoxideAverageData;
  private float performanceAverageData;
}
