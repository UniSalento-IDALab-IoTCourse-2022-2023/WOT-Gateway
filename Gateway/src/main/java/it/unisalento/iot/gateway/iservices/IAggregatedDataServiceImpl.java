package it.unisalento.iot.gateway.iservices;

import it.unisalento.iot.gateway.domains.AggregatedData;

import java.util.Scanner;

public interface IAggregatedDataServiceImpl {

  /**
   * metodo per creare un aggregatedData
   */
  AggregatedData createAggregatedData();
}
