import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UserAccountComponent} from "./user-account.component";

const routes: Routes = [
  {
    path: '',
    component: UserAccountComponent,
    data: {
      title: $localize`UserAccount`
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserAccountRoutingModule {
}
