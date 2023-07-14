import { LayoutModule } from '@angular/cdk/layout';
import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatCardModule } from '@angular/material/card';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatToolbarModule } from '@angular/material/toolbar';

import { WebBluetoothModule } from '@manekinekko/angular-web-bluetooth';
import { AppComponent } from './app.component';
import { BatteryLevelComponent } from './thingy52/battery-level.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { HumidityComponent } from './thingy52/humidity.component';
import { StepCounterComponent } from './thingy52/stepcounter.component';
import { TemperatureComponent } from './thingy52/temperature.component';
import { BrowserModule } from '@angular/platform-browser';
import { TempComponent } from './raspberry/temp/temp.component';
import { PressureComponent } from './thingy52/pressure.component';
import { YieldComponent } from './thingy52/yield.component';
import { CarbonComponent } from './thingy52/carbon.component';
import { RawdataService } from './services/rawdata.service';
import { HttpClientModule } from '@angular/common/http';
import { StateComponent } from './thingy52/state-component';

@NgModule({
  declarations: [
    AppComponent,
    BatteryLevelComponent,
    TemperatureComponent,
    YieldComponent,
    HumidityComponent,
    PressureComponent,
    StepCounterComponent,
    DashboardComponent,
    TempComponent,
    CarbonComponent,
    StateComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    WebBluetoothModule.forRoot({
      enableTracing: true
    }),
    LayoutModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatListModule,
    MatExpansionModule,
    MatSnackBarModule,
    MatProgressSpinnerModule,
    MatGridListModule,
    MatCardModule,
    MatMenuModule,
    HttpClientModule
  ],
  providers: [
    RawdataService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
