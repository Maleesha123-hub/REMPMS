import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AchievementsComponent} from "./achievements/achievements.component";
import {PersonalDetailsComponent} from "./personal-details/personal-details.component";
import {ProfessionalExperiencesComponent} from "./professional-experiences/professional-experiences.component";
import {HigherEducationComponent} from "./higher-education/higher-education.component";
import {SchoolEducationComponent} from "./school-education/school-education.component";
import {MembershipComponent} from "./membership/membership.component";
import {LanguageProficiencyComponent} from "./language-proficiency/language-proficiency.component";
import {ResearchComponent} from "./research/research.component";
import {RefereesComponent} from "./referees/referees.component";
import {FamilyInformationComponent} from "./family-information/family-information.component";
import {JobPreferencesComponent} from "./job-preferences/job-preferences.component";
import {CvOrCertificatesComponent} from "./cv-or-certificates/cv-or-certificates.component";
import {PreferredJobLocationComponent} from "./preferred-job-location/preferred-job-location.component";

const routes: Routes = [
  {
    path: '',
    data: {
      title: $localize`CommonProfile`
    },
    children: [
      {
        path: 'personal-details',
        component: PersonalDetailsComponent,
        data: {
          title: 'Personal Details',
        },
      }, {
        path: 'professional-experiences',
        component: ProfessionalExperiencesComponent,
        data: {
          title: 'Professional Experiences',
        },
      }, {
        path: 'higher-education',
        component: HigherEducationComponent,
        data: {
          title: 'Higher Education',
        },
      }, {
        path: 'school-education',
        component: SchoolEducationComponent,
        data: {
          title: 'School Education',
        },
      }, {
        path: 'membership',
        component: MembershipComponent,
        data: {
          title: 'Membership',
        },
      }, {
        path: 'language-proficiency',
        component: LanguageProficiencyComponent,
        data: {
          title: 'Language Proficiency',
        },
      }, {
        path: 'research',
        component: ResearchComponent,
        data: {
          title: 'Research',
        },
      }, {
        path: 'achievements',
        component: AchievementsComponent,
        data: {
          title: 'Achievements',
        },
      }, {
        path: 'referees',
        component: RefereesComponent,
        data: {
          title: 'Referees',
        },
      }, {
        path: 'family-information',
        component: FamilyInformationComponent,
        data: {
          title: 'Family Information',
        },
      }, {
        path: 'job-preferences',
        component: JobPreferencesComponent,
        data: {
          title: 'Job Preference',
        },
      }, {
        path: 'preferred-job-location',
        component: PreferredJobLocationComponent,
        data: {
          title: 'Preferred Job Location',
        },
      }, {
        path: 'cv-or-certificates',
        component: CvOrCertificatesComponent,
        data: {
          title: 'Uploaded CVs/Certificates',
        },
      },
    ],
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CommonProfileRoutingModule {
}
