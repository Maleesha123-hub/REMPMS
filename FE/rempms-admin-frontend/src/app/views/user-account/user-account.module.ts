import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ReactiveFormsModule} from '@angular/forms';

import {
  AvatarModule,
  ButtonGroupModule,
  ButtonModule,
  CardModule,
  FormModule,
  GridModule,
  NavModule,
  ProgressModule,
  TableModule,
  TabsModule
} from '@coreui/angular';
import {IconModule} from '@coreui/icons-angular';
import {ChartjsModule} from '@coreui/angular-chartjs';
import {UserAccountRoutingModule} from "./user-account-routing.module";
import {UserAccountComponent} from "./user-account.component";
import {DocsComponentsModule} from "@docs-components/docs-components.module";

@NgModule({
  imports: [
    UserAccountRoutingModule,
    CardModule,
    NavModule,
    IconModule,
    TabsModule,
    CommonModule,
    GridModule,
    ProgressModule,
    ReactiveFormsModule,
    ButtonModule,
    FormModule,
    ButtonModule,
    ButtonGroupModule,
    ChartjsModule,
    AvatarModule,
    TableModule,
    DocsComponentsModule
  ],
  declarations: [UserAccountComponent]
})
export class UserAccountModule {
}
