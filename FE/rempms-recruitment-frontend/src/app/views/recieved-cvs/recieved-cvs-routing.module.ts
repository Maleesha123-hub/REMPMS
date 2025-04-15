import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RecievedCvsComponent } from './recieved-cvs.component';
import { CvsPageComponent } from './cvs-page/cvs-page.component';

const routes: Routes = [
  {
    path: '',
    component: RecievedCvsComponent,
    data: {
      title: $localize`RecievedCvs`,
    },
  },
  { path: 'cvspage', component: CvsPageComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class RecievedCvsRoutingModule {}
