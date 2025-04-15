import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { JobAlertComponent } from './job-alert/job-alert.component';

const routes: Routes = [
  {
    path: '',
    data: {
      title: $localize`MyPreferences`,
    },
    children: [
      {
        path: 'job-alert',
        component: JobAlertComponent,
        data: {
          title: 'Job Alert',
        },
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MyPreferencesRoutingModule {}
