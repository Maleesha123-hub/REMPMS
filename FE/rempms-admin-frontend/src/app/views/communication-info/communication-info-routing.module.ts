import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CommunicationInfoComponent} from "./communication-info.component";

const routes: Routes = [
  {
    path: '',
    component: CommunicationInfoComponent,
    data: {
      title: $localize`CommunicationInfo`
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CommunicationInfoRoutingModule {
}
