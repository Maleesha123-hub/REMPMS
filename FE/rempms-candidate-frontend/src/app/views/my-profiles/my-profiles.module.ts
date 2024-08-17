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
import {DocsComponentsModule} from "@docs-components/docs-components.module";
import {MyProfilesRoutingModule} from "./my-profiles-routing.module";
import {MyCvDocumentComponent} from "./my-cv-document/my-cv-document.component";
import {MyLoginComponent} from "./my-login/my-login.component";

@NgModule({
  imports: [
    MyProfilesRoutingModule,
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
  declarations: [MyCvDocumentComponent, MyLoginComponent]
})
export class MyProfilesModule {
}
