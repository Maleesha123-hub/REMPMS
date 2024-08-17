import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ReactiveFormsModule} from '@angular/forms';

import {
  AvatarModule,
  ButtonGroupModule,
  ButtonModule,
  CardModule,
  FormModule,
  GridModule,
  NavModule,
  ProgressModule,
  TableModule,
  TabsModule
} from '@coreui/angular';
import {IconModule} from '@coreui/icons-angular';
import {ChartjsModule} from '@coreui/angular-chartjs';
import {LocationInfoRoutingModule} from "./location-info-routing.module";
import {DocsComponentsModule} from "@docs-components/docs-components.module";
import {LocationInfoComponent} from "./location-info.component";
import {CountryComponent} from "./country/country.component";
import {CityComponent} from './city/city.component';
import {DistrictComponent} from './district/district.component';
import {ProvinceComponent} from './province/province.component';
import {LocationInformationComponent} from './location-information/location-information.component';

@NgModule({
  imports: [
    LocationInfoRoutingModule,
    CardModule,
    NavModule,
    IconModule,
    TabsModule,
    CommonModule,
    GridModule,
    ProgressModule,
    ReactiveFormsModule,
    ButtonModule,
    FormModule,
    ButtonModule,
    ButtonGroupModule,
    ChartjsModule,
    AvatarModule,
    TableModule,
    DocsComponentsModule
  ],
  declarations: [LocationInfoComponent, CountryComponent, CityComponent, DistrictComponent, ProvinceComponent, LocationInformationComponent]
})
export class LocationInfoModule {
}
