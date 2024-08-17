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
import {MyJobsRoutingModule} from "./my-jobs-routing.module";
import {FlaggedJobsComponent} from './flagged-jobs/flagged-jobs.component';
import {PreferredCompaniesComponent} from "./preferred-companies/preferred-companies.component";

@NgModule({
  imports: [
    MyJobsRoutingModule,
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
  declarations: [FlaggedJobsComponent, PreferredCompaniesComponent]
})
export class MyJobsModule {
}
