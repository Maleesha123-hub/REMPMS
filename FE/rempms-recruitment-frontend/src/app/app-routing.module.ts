import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { DefaultLayoutComponent } from './containers';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'dashboard',
    pathMatch: 'full',
  },
  {
    path: '',
    component: DefaultLayoutComponent,
    data: {
      title: 'Home',
    },
    children: [
      {
        path: 'dashboard',
        loadChildren: () =>
          import('./views/dashboard/dashboard.module').then(
            (m) => m.DashboardModule
          ),
      },
      {
        path: 'employer',
        loadChildren: () =>
          import('./views/employer/employer.module').then(
            (m) => m.EmployerModule
          ),
      },
      {
        path: 'job-position',
        loadChildren: () =>
          import('./views/job-position/job-position.module').then(
            (m) => m.JobPositionModule
          ),
      },
      {
        path: 'job-vacancy',
        loadChildren: () =>
          import('./views/job-vacancy/job-vacancy.module').then(
            (m) => m.JobVacancyModule
          ),
      },
      {
        path: 'received-cvs',
        loadChildren: () =>
          import('./views/recieved-cvs/recieved-cvs.module').then(
            (m) => m.RecievedCvsModule
          ),
      },
    ],
  },
  { path: '**', redirectTo: 'dashboard' },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, {
      scrollPositionRestoration: 'top',
      anchorScrolling: 'enabled',
      initialNavigation: 'enabledBlocking',
      // relativeLinkResolution: 'legacy'
    }),
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
