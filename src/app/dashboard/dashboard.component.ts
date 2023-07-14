import { Component } from '@angular/core';
import { RawData } from '../models/rawdata';
import { Subscription, interval } from 'rxjs';
import { RawdataService } from '../services/rawdata.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'ble-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent {

  rawData: RawData = {
    carbonMonoxideRawData: 0,
    temperatureRawData: 0,
    pressureRawData: 0,
    performanceRawData: 0,
    stateRawData: 0
  };

  private carbonMonoxideSubscription: Subscription;
  private temperatureSubscription: Subscription;
  private pressureSubscription: Subscription;
  private performanceSubscription: Subscription;
  private stateSubscription: Subscription;
  private postSubscription: Subscription;

  constructor(
    private rawdataService: RawdataService,
    private http : HttpClient
  ) {}

  ngOnInit() {
    this.carbonMonoxideSubscription = this.rawdataService.carbonMonoxideData$.subscribe(
      data => {
        this.rawData.carbonMonoxideRawData = data;
      }
    );

    this.pressureSubscription = this.rawdataService.pressureData$.subscribe(
      data => {
        this.rawData.pressureRawData = data;
      }
    );

    this.temperatureSubscription = this.rawdataService.temperatureData$.subscribe(
      data => {
        this.rawData.temperatureRawData = data;
      }
    );

    this.performanceSubscription = this.rawdataService.performanceData$.subscribe(
      data => {
        this.rawData.performanceRawData = data;
      }
    );

    this.stateSubscription = this.rawdataService.stateData$.subscribe(
      data => {
        this.rawData.stateRawData = data;
      }
    );

    this.postSubscription = interval(30000).subscribe(() => {
      this.rawdataService.sendRawData(this.rawData);
      console.log(this.rawData);
    });
  }



  ngOnDestroy() {
    this.carbonMonoxideSubscription.unsubscribe();
    this.postSubscription.unsubscribe();
  }
}
