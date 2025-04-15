import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './views/pages/login/login.component';
import { RegisterComponent } from './views/pages/register/register.component';
import { Page500Component } from './views/pages/page500/page500.component';

import { DefaultLayoutComponent } from './containers';
import { AuthGuard } from './auth/auth.guard';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'login',
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
            (m) => m.DashboardModule,
          ),
        canActivate: [AuthGuard],
      },
      {
        path: 'employer',
        loadChildren: () =>
          import('./views/employer/employer.module').then(
            (m) => m.EmployerModule,
          ),
        canActivate: [AuthGuard],
      },
      {
        path: 'job-position',
        loadChildren: () =>
          import('./views/job-position/job-position.module').then(
            (m) => m.JobPositionModule,
          ),
        canActivate: [AuthGuard],
      },
      {
        path: 'job-vacancy',
        loadChildren: () =>
          import('./views/job-vacancy/job-vacancy.module').then(
            (m) => m.JobVacancyModule,
          ),
        canActivate: [AuthGuard],
      },
      {
        path: 'received-cvs',
        loadChildren: () =>
          import('./views/recieved-cvs/recieved-cvs.module').then(
            (m) => m.RecievedCvsModule,
          ),
        canActivate: [AuthGuard],
      },
    ],
  },
  {
    path: 'unauthorized',
    component: Page500Component,
    data: {
      title: 'Unauthorized Page',
    },
  },
  {
    path: 'login',
    component: LoginComponent,
    data: {
      title: 'Login Page',
    },
  },
  {
    path: 'register',
    component: RegisterComponent,
    data: {
      title: 'Register Page',
    },
  },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, {
      useHash: true,
      scrollPositionRestoration: 'top',
      anchorScrolling: 'enabled',
      initialNavigation: 'enabledBlocking',
      // relativeLinkResolution: 'legacy'
    }),
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
