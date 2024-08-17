import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {DefaultLayoutComponent} from './containers';

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
        path: 'dashboard',
        loadChildren: () =>
          import('./views/dashboard/dashboard.module').then((m) => m.DashboardModule)
      },
      {
        path: 'user-account',
        loadChildren: () =>
          import('./views/user-account/user-account.module').then((m) => m.UserAccountModule)
      },
      {
        path: 'user-role',
        loadChildren: () =>
          import('./views/user-role/user-role.module').then((m) => m.UserRoleModule)
      },
      {
        path: 'communication-info',
        loadChildren: () =>
          import('./views/communication-info/communication-info.module').then((m) => m.CommunicationInfoModule)
      },
      {
        path: 'location-info',
        loadChildren: () =>
          import('./views/location-info/location-info.module').then((m) => m.LocationInfoModule)
      },
      {
        path: 'job-category',
        loadChildren: () =>
          import('./views/job-category/job-category.module').then((m) => m.JobCategoryModule)
      }
    ]
  },
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
