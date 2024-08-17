import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {JobCategoryComponent} from "./job-category.component";

const routes: Routes = [
  {
    path: '',
    component: JobCategoryComponent,
    data: {
      title: $localize`JobCategory`
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class JobCategoryRoutingModule {
}
