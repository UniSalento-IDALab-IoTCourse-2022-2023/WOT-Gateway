import { Injectable } from '@angular/core';
import { BluetoothCore } from '@manekinekko/angular-web-bluetooth';
import { map, mergeMap } from 'rxjs/operators';

type ServiceOptions = {
    characteristic: string;
    service: string,
    decoder(value: DataView): number | {[key: string]: number}
};

@Injectable({
  providedIn: 'root'
})
export class BleService {

    // tslint:disable-next-line: variable-name
    private _config: ServiceOptions;

  constructor(public ble: BluetoothCore) {}

  config(options: ServiceOptions) {
    this._config = options;
  }

  getDevice() {
    return this.ble.getDevice$();
  }

  stream() {
    return this.ble.streamValues$().pipe(
      map(this._config.decoder)
    );
  }

  value() {
    return this.ble
      .discover$({
        // acceptAllDevices: true,
        filters: [{ name: "Boiler" }],
        optionalServices: [this._config.service]
      })        
      .pipe(
        // 2) get that service
        mergeMap((gatt: BluetoothRemoteGATTServer) => {
          return this.ble.getPrimaryService$(gatt, this._config.service);
        }),

        // 3) get a specific characteristic on that service
        mergeMap((primaryService: BluetoothRemoteGATTService) => {
          return this.ble.getCharacteristic$(primaryService, this._config.characteristic);
        }),

        // 4) ask for the value of that characteristic (will return a DataView)
        mergeMap((characteristic: BluetoothRemoteGATTCharacteristic) => {
          return this.ble.readValue$(characteristic);
        }),

        // 5) on that DataView, get the right value
        map(this._config.decoder)
      )
      // .value$({
      //   acceptAllDevices: true,
      //   service: this._config.service,
      //   characteristic: this._config.characteristic
      // })
      // .pipe(
      //   map(this._config.decoder)
      // );

  }

  disconnectDevice() {
    this.ble.disconnectDevice();
  }
}
