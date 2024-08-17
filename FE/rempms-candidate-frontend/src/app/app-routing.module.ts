import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { DefaultLayoutComponent } from './containers';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'dashboard',
    pathMatch: 'full'
  },
  {
    path: '',
    component: DefaultLayoutComponent,
    data: {
      title: 'Home'
    },
    children: [
      {
        path: 'job-vacancies',
        loadChildren: () =>
          import('./views/job-vacancies/job-vacancies.module').then((m) => m.JobVacanciesModule)
      },
      {
        path: 'my-jobs',
        loadChildren: () =>
          import('./views/my-jobs/my-jobs.module').then((m) => m.MyJobsModule)
      },{
        path: 'my-profiles',
        loadChildren: () =>
          import('./views/my-profiles/my-profiles.module').then((m) => m.MyProfilesModule)
      },{
        path: 'my-preferences',
        loadChildren: () =>
          import('./views/my-preferences/my-preferences.module').then((m) => m.MyPreferencesModule)
      },{
        path: 'common-profile',
        loadChildren: () =>
          import('./views/my-profiles/common-profile/common-profile.module').then((m) => m.CommonProfileModule)
      }
      // {
      //   path: 'buttons',
      //   loadChildren: () =>
      //     import('./views/buttons/buttons.module').then((m) => m.ButtonsModule)
      // },
      // {
      //   path: 'forms',
      //   loadChildren: () =>
      //     import('./views/forms/forms.module').then((m) => m.CoreUIFormsModule)
      // },
      // {
      //   path: 'job-vacancies',
      //   loadChildren: () =>
      //     import('./views/job-vacancies/job-vacancies.module').then((m) => m.MyJobsModule)
      // },
      // {
      //   path: 'charts',
      //   loadChildren: () =>
      //     import('./views/charts/charts.module').then((m) => m.ChartsModule)
      // },
      // {
      //   path: 'icons',
      //   loadChildren: () =>
      //     import('./views/icons/icons.module').then((m) => m.IconsModule)
      // },
      // {
      //   path: 'notifications',
      //   loadChildren: () =>
      //     import('./views/notifications/notifications.module').then((m) => m.NotificationsModule)
      // },
      // {
      //   path: 'widgets',
      //   loadChildren: () =>
      //     import('./views/widgets/widgets.module').then((m) => m.WidgetsModule)
      // },
      // {
      //   path: 'pages',
      //   loadChildren: () =>
      //     import('./views/pages/pages.module').then((m) => m.PagesModule)
      // },
    ]
  },
  // {
  //   path: '404',
  //   component: Page404Component,
  //   data: {
  //     title: 'Page 404'
  //   }
  // },
  // {
  //   path: '500',
  //   component: Page500Component,
  //   data: {
  //     title: 'Page 500'
  //   }
  // },
  // {
  //   path: 'login',
  //   component: LoginComponent,
  //   data: {
  //     title: 'Login Page'
  //   }
  // },
  // {
  //   path: 'register',
  //   component: RegisterComponent,
  //   data: {
  //     title: 'Register Page'
  //   }
  // },
  {path: '**', redirectTo: 'dashboard'}
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, {
      scrollPositionRestoration: 'top',
      anchorScrolling: 'enabled',
      initialNavigation: 'enabledBlocking'
      // relativeLinkResolution: 'legacy'
    })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
