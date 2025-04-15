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
import { DocsComponentsModule } from '@docs-components/docs-components.module';
import { CommonProfileRoutingModule } from './common-profile-routing.module';
import { AchievementsComponent } from './achievements/achievements.component';
import { CvOrCertificatesComponent } from './cv-or-certificates/cv-or-certificates.component';
import { FamilyInformationComponent } from './family-information/family-information.component';
import { HigherEducationComponent } from './higher-education/higher-education.component';
import { JobPreferencesComponent } from './job-preferences/job-preferences.component';
import { LanguageProficiencyComponent } from './language-proficiency/language-proficiency.component';
import { MembershipComponent } from './membership/membership.component';
import { PersonalDetailsComponent } from './personal-details/personal-details.component';
import { PreferredJobLocationComponent } from './preferred-job-location/preferred-job-location.component';
import { ProfessionalExperiencesComponent } from './professional-experiences/professional-experiences.component';
import { RefereesComponent } from './referees/referees.component';
import { ResearchComponent } from './research/research.component';
import { SchoolEducationComponent } from './school-education/school-education.component';
import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';
import { CommonProfileEffects } from './store/common-profile.effects';
import { commonProfileReducer } from './store/common-profile.reducer';

@NgModule({
  imports: [
    CommonProfileRoutingModule,
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

    StoreModule.forFeature('commonProfileState', commonProfileReducer),
    EffectsModule.forFeature([CommonProfileEffects]),
  ],
  declarations: [
    AchievementsComponent,
    CvOrCertificatesComponent,
    FamilyInformationComponent,
    HigherEducationComponent,
    JobPreferencesComponent,
    LanguageProficiencyComponent,
    MembershipComponent,
    PersonalDetailsComponent,
    PreferredJobLocationComponent,
    ProfessionalExperiencesComponent,
    RefereesComponent,
    ResearchComponent,
    SchoolEducationComponent,
  ],
})
export class CommonProfileModule {}
