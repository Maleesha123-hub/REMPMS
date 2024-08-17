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
import {} from './job-position-routing.module';
import { DocsComponentsModule } from '@docs-components/docs-components.module';
import { JobPositionComponent } from './job-position.component';
import { JobPositionRoutingModule } from './job-position-routing.module';
import { StoreModule } from '@ngrx/store';
import { jobPositionReducer } from './store/job-position.reducer';
import { EffectsModule } from '@ngrx/effects';
import { JobPositionEffects } from './store/job-position.effects';
import { EmployerEffects } from '../employer/store/employer.effects';

@NgModule({
  imports: [
    JobPositionRoutingModule,
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

    StoreModule.forFeature('jobPositionState', jobPositionReducer),
    EffectsModule.forFeature([JobPositionEffects]),
  ],
  declarations: [JobPositionComponent],
})
export class JobPositionModule {}
