import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Subject } from 'rxjs/internal/Subject';
import { RawData } from '../models/rawdata';

@Injectable({
  providedIn: 'root'
})
export class RawdataService {

  constructor(private http: HttpClient) { }
  
  private carbonMonoxideDataSubject = new Subject<number>();
  private pressureDataSubject = new Subject<number>();
  private temperatureDataSubject = new Subject<number>();
  private performanceDataSubject = new Subject<number>();
  private stateDataSubject = new Subject<number>();

  carbonMonoxideData$ = this.carbonMonoxideDataSubject.asObservable();
  pressureData$ = this.pressureDataSubject.asObservable();
  temperatureData$ = this.temperatureDataSubject.asObservable();
  performanceData$ = this.performanceDataSubject.asObservable();
  stateData$ = this.stateDataSubject.asObservable();
  
  updateCarbonMonoxideData(data: number) {
    this.carbonMonoxideDataSubject.next(data);
  }

  updatePressureData(data: number) {
    this.pressureDataSubject.next(data);
  }

  updateTemperatureData(data: number) {
    this.temperatureDataSubject.next(data);
  }

  updatePerformanceData(data: number) {
    this.performanceDataSubject.next(data);
  }

  updateStateData(data: number) {
    this.stateDataSubject.next(data);
  }

  sendRawData(data: RawData) {
    // Perform the POST request with this.carbonMonoxideData
    // Replace the URL with the actual backend endpoint
    this.http.post('http://localhost:8080/api/gateway/boiler/saveRawData', data).subscribe(
      response => {
        // Handle the response if needed
      },
      error => {
        // Handle the error if the POST request fails
      }
    );
  }
}
