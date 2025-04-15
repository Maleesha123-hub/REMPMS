import { NgModule } from '@angular/core';
import { EmployerVisibilityToggle } from '../directives/employer/EmployerVisibilityToggle.directive';

@NgModule({
  declarations: [EmployerVisibilityToggle],
  exports: [EmployerVisibilityToggle],
})
export class SharedModule {}
