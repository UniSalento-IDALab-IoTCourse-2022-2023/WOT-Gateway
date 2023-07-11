package it.unisalento.iot.gateway.domains;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Document("boiler")
@Getter
@Setter
@NoArgsConstructor
public class Boiler implements Serializable {

//  @Value("${boiler.id}")
  @Id private String id;
  @CreatedDate private Date date;
}
