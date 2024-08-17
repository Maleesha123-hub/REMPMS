import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

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
  TabsModule,
} from '@coreui/angular';
import { IconModule } from '@coreui/icons-angular';
import { ChartjsModule } from '@coreui/angular-chartjs';
import {} from './employer-routing.module';
import { DocsComponentsModule } from '@docs-components/docs-components.module';
import { EmployerComponent } from './employer.component';
import { EmployerRoutingModule } from './employer-routing.module';
import { StoreModule } from '@ngrx/store';
import { employerReducer } from './store/employer.reducer';
import { EffectsModule } from '@ngrx/effects';
import { EmployerEffects } from './store/employer.effects';

@NgModule({
  imports: [
    EmployerRoutingModule,
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
    DocsComponentsModule,

    StoreModule.forFeature('employerState', employerReducer),
    EffectsModule.forFeature([EmployerEffects]),
  ],

  declarations: [EmployerComponent],
})
export class EmployerModule {}
