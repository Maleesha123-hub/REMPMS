import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FlaggedJobsComponent } from './flagged-jobs/flagged-jobs.component';
import { PreferredCompaniesComponent } from './preferred-companies/preferred-companies.component';

const routes: Routes = [
  {
    path: '',
    data: {
      title: $localize`MyJobs`,
    },
    children: [
      {
        path: 'flagged-jobs',
        component: FlaggedJobsComponent,
        data: {
          title: 'Flagged Jobs',
        },
      },
      {
        path: 'preferred-companies',
        component: PreferredCompaniesComponent,
        data: {
          title: 'Preferred Companies',
        },
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MyJobsRoutingModule {}
