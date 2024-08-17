import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {JobVacanciesComponent} from "./job-vacancies.component";

const routes: Routes = [
  {
    path: '',
    component: JobVacanciesComponent,
    data: {
      title: $localize`JobVacancies`
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class JobVacanciesRoutingModule {
}
