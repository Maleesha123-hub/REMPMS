import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { JobVacancyComponent } from './job-vacancy.component';

const routes: Routes = [
  {
    path: '',
    component: JobVacancyComponent,
    data: {
      title: $localize`JobVancancy`,
    },
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class JobVancancyRoutingModule {}
