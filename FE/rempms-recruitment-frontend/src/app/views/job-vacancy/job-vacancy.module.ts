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
import { JobVancancyRoutingModule } from './job-vacancy-routing.module';
import { DocsComponentsModule } from '@docs-components/docs-components.module';
import { JobVacancyComponent } from './job-vacancy.component';
import { StoreModule } from '@ngrx/store';
import { jobPositionReducer } from '../job-position/store/job-position.reducer';
import { jobVacancyReducer } from '../job-vacancy/store/job.vacancy.reducer';
import { EffectsModule } from '@ngrx/effects';
import { JobPositionEffects } from '../job-position/store/job-position.effects';
import { JobVacancyEffects } from '../job-vacancy/store/job.vacancy.effects';
import { EmployerEffects } from '../employer/store/employer.effects';
import { employerReducer } from '../employer/store/employer.reducer';

@NgModule({
  imports: [
    JobVancancyRoutingModule,
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
    StoreModule.forFeature('employerState', employerReducer),
    StoreModule.forFeature('jobVacancyState', jobVacancyReducer),
    EffectsModule.forFeature([
      JobPositionEffects,
      EmployerEffects,
      JobVacancyEffects,
    ]),
  ],
  declarations: [JobVacancyComponent],
})
export class JobVacancyModule {}
