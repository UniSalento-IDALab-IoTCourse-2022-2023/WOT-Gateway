package it.unisalento.iot.gateway.services.impl;

import it.unisalento.iot.gateway.domains.AggregatedData;
import it.unisalento.iot.gateway.domains.RawData;
import it.unisalento.iot.gateway.iservices.IAggregatedDataServiceImpl;
import it.unisalento.iot.gateway.repositories.IAggregatedDataRepository;
import it.unisalento.iot.gateway.repositories.IRawDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

/**
 * classe per specificare i servizi dei dati aggregati
 */
@Service
public class AggregatedDataService implements IAggregatedDataServiceImpl {

  @Autowired
  IAggregatedDataRepository aggregatedDataRepository;

  @Override
  public AggregatedData createAggregatedData(List<RawData> rawDataList ) {
    AggregatedData newAggregatedData = new AggregatedData();

    newAggregatedData.setBoilerId("64ab11356623471faa234a7a");

    // effettuo la media dei valori di:

    // temperatureRawData
    newAggregatedData.setTemperatureAverageData((float)rawDataList.stream()
      .mapToDouble(RawData::getTemperatureRawData)
      .average()
      .orElse(0.0));

    // pressureRawData
    newAggregatedData.setPressureAverageData((float)rawDataList.stream()
      .mapToDouble(RawData::getPressureRawData)
      .average()
      .orElse(0.0));

    // carbonMonoxideRawData
    newAggregatedData.setCarbonMonoxideAverageData((float)rawDataList.stream()
      .mapToDouble(RawData::getCarbonMonoxideRawData)
      .average()
      .orElse(0.0));

    // performanceRawData
    newAggregatedData.setPerformanceAverageData((float)rawDataList.stream()
      .mapToDouble(RawData::getPerformanceRawData)
      .average()
      .orElse(0.0));

    // save dell'oggetto creato
    AggregatedData saved = aggregatedDataRepository.save(newAggregatedData);
    newAggregatedData.setId(saved.getId());

    return newAggregatedData;
  }
}
