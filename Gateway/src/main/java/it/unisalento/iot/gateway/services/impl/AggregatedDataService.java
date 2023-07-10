package it.unisalento.iot.gateway.services.impl;

import it.unisalento.iot.gateway.domains.AggregatedData;
import it.unisalento.iot.gateway.domains.CarbonMonoxideRawData;
import it.unisalento.iot.gateway.domains.PerformanceRawData;
import it.unisalento.iot.gateway.iservices.IAggregatedDataServiceImpl;
import it.unisalento.iot.gateway.repositories.IAggregatedDataRepository;
import it.unisalento.iot.gateway.repositories.ICarbonMonoxideRepository;
import it.unisalento.iot.gateway.repositories.IPerformanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.Scanner;

/**
 * classe per specificare i servizi dei dati aggregati
 */
@Service
public class AggregatedDataService implements IAggregatedDataServiceImpl {

  @Autowired
  IPerformanceRepository performanceRepository;
  @Autowired
  ICarbonMonoxideRepository carbonMonoxideRepository;
  @Autowired
  IAggregatedDataRepository aggregatedDataRepository;
  @Autowired
  private MongoTemplate mongoTemplate;

  @Override
  public AggregatedData createAggregatedData() {
    AggregatedData newAggregatedData = new AggregatedData();

    System.out.println("Inserire l'id del boiler: ");
    newAggregatedData.setBoilerId("64ab11356623471faa234a7a");

    // prendo i dati raw dal db
    List<PerformanceRawData> performanceRawDataList = performanceRepository.findAll();
    List<CarbonMonoxideRawData> carbonMonoxideRawDataList = carbonMonoxideRepository.findAll();

    // effettuo la media dei valori di:

    // performanceRawDataList
    newAggregatedData.setPerformanceAverageData((float)performanceRawDataList.stream()
      .mapToDouble(PerformanceRawData::getPerformanceRawData)
      .average()
      .orElse(0.0));

    // carbonMonoxideRawDataList
    newAggregatedData.setCoAverageData((float)carbonMonoxideRawDataList.stream()
      .mapToDouble(CarbonMonoxideRawData::getCarbonMonoxideRawData)
      .average()
      .orElse(0.0));

    // save dell'oggetto creato
    AggregatedData saved = aggregatedDataRepository.save(newAggregatedData);
    newAggregatedData.setId(saved.getId());

    // delete dei dati raw usati
    mongoTemplate.dropCollection("performanceRawData");
    mongoTemplate.dropCollection("carbonMonoxideRawData");

    return newAggregatedData;
  }
}
