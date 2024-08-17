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
import {DocsComponentsModule} from "@docs-components/docs-components.module";
import {MyPreferencesRoutingModule} from "./my-preferences-routing.module";
import {JobAlertComponent} from "./job-alert/job-alert.component";

@NgModule({
  imports: [
    MyPreferencesRoutingModule,
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
  declarations: [JobAlertComponent]
})
export class MyPreferencesModule {
}
