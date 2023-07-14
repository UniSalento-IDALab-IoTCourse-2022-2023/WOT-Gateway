import { Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { BluetoothCore, BrowserWebBluetooth, ConsoleLoggerService } from '@manekinekko/angular-web-bluetooth';
import { Subscription } from 'rxjs';
import { BleService } from '../ble.service';
import { RawdataService } from '../services/rawdata.service';

export const bleCore = (b: BrowserWebBluetooth, l: ConsoleLoggerService) => new BluetoothCore(b, l);
export const bleService = (b: BluetoothCore) => new BleService(b);

// make sure we get a singleton instance of each service
const PROVIDERS = [{
  provide: BluetoothCore,
  useFactory: bleCore,
  deps: [BrowserWebBluetooth, ConsoleLoggerService]
}, {
  provide: BleService,
  useFactory: bleService,
  deps: [BluetoothCore]
}];

@Component({
  selector: 'ble-state',
  template: `

    <div *ngIf="(device | async) && !isAlert">
        <h1 *ngIf="(device | async) && !isAlert">Stato: OK</h1>
        <mat-icon>sentiment_satisfied_alt</mat-icon>
    </div>

    <div *ngIf="(device | async) && isAlert">
        <h1>Id allarme: {{ value }}</h1>
        <mat-icon>sick</mat-icon>
        <h5>Si prega di contattare l'assistenza</h5>
    </div>
  `,
  styles: [`
  :host {
    display: block;
  }
  canvas {
    margin-left: -16px;
  }`],
  providers: PROVIDERS
})
export class StateComponent implements OnInit, OnDestroy {
  isAlert: boolean = false;
  valuesSubscription: Subscription;
  streamSubscription: Subscription;
  value : number = 0;

  get device() {
    return this.service.getDevice();
  }

  constructor(
    public service: BleService,
    public snackBar: MatSnackBar,
    private rawdataService: RawdataService) {

    service.config({
      decoder: (value: DataView) => value.getInt16(0, true),
      characteristic: 'aef11e22-00c2-4a5c-8aa9-c2e8d7d8034b',
      service: 'user_data',
    });

  }

  ngOnInit() {
    this.streamSubscription = this.service.stream()
      .subscribe({
        next: (val: number) => {
          this.updateValue(val);
        },
        error: (err) => this.hasError(err)
      });
  }

  requestValue() {
    this.valuesSubscription = this.service.value()
      .subscribe(() => null, error => this.hasError.bind(this));
  }

  updateValue(value: number) {
    console.log('Reading state %d', value);
    this.isAlert = this.checkForAlert(value);
    this.updateStateData(value)
    this.value = value; 
  }

  disconnect() {
    this.service.disconnectDevice();
    this.valuesSubscription.unsubscribe();
  }

  hasError(error: string) {
    this.snackBar.open(error, 'Close');
  }

  ngOnDestroy() {
    this.valuesSubscription.unsubscribe();
    this.streamSubscription.unsubscribe();
  }

  checkForAlert(value: number): boolean {
    return value != 0;
  }

  updateStateData(value: number) {
    // Update the state  data in the service
    this.rawdataService.updateStateData(value);
  }
}