import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LocationInfoComponent} from "./location-info.component";

const routes: Routes = [
  {
    path: '',
    component: LocationInfoComponent,
    data: {
      title: $localize`LocationInfo`
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LocationInfoRoutingModule {
}
