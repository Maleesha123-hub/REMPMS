import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {UserRoleComponent} from "./user-role.component";

const routes: Routes = [
  {
    path: '',
    component: UserRoleComponent,
    data: {
      title: $localize`UserRole`
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoleRoutingModule {
}
