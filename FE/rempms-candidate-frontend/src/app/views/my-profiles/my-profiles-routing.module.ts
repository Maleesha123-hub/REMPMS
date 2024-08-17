import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MyCvDocumentComponent} from "./my-cv-document/my-cv-document.component";
import {MyLoginComponent} from "./my-login/my-login.component";

const routes: Routes = [
  {
    path: '',
    data: {
      title: $localize`MyProfiles`
    },
    children: [
      {
        path: 'my-cv-document',
        component: MyCvDocumentComponent,
        data: {
          title: 'My CV Document(s)',
        },
      },
      {
        path: 'my-login',
        component: MyLoginComponent,
        data: {
          title: 'My Login',
        },
      }
    ],
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MyProfilesRoutingModule {
}
