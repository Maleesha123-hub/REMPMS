import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { JobPositionComponent } from './job-position.component';

const routes: Routes = [
  {
    path: '',
    component: JobPositionComponent,
    data: {
      title: $localize`JobPosition`,
    },
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class JobPositionRoutingModule {}
