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
import { RecievedCvsRoutingModule } from './recieved-cvs-routing.module';
import { DocsComponentsModule } from '@docs-components/docs-components.module';
import { RecievedCvsComponent } from './recieved-cvs.component';
import { CvsPageComponent } from './cvs-page/cvs-page.component';
import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';
import { JobVacancyEffects } from '../job-vacancy/store/job.vacancy.effects';
import { RecievedCvsEffects } from './store/recieved-cvs.effects';
import { jobVacancyReducer } from '../job-vacancy/store/job.vacancy.reducer';
import { recievedCvsReducer } from './store/recieved-cvs.reducer';

@NgModule({
  imports: [
    RecievedCvsRoutingModule,
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

    StoreModule.forFeature('jobVacancyState', jobVacancyReducer),
    StoreModule.forFeature('recievedCvsState', recievedCvsReducer),
    EffectsModule.forFeature([JobVacancyEffects, RecievedCvsEffects]),
  ],
  declarations: [RecievedCvsComponent, CvsPageComponent],
})
export class RecievedCvsModule {}
