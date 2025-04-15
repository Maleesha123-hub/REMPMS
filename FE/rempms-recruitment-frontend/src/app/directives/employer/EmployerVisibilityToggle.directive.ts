import { Directive, ElementRef, Input, OnInit, Renderer2 } from '@angular/core';
import { UserDetailsResponseDTO } from '../../model/user/user-details/UserDetailsResponseDTO';
import { Module } from '../../enums/Module';
import { Component } from '../../enums/Component';
import { EmployerElement } from '../../enums/elements/EmployerElement';

@Directive({
  selector: '[employerVisibilityToggle]',
})
export class EmployerVisibilityToggle implements OnInit {
  @Input('employerVisibilityToggle') visibilityId: string | undefined;

  constructor(
    private element: ElementRef,
    private renderer: Renderer2,
  ) {}

  ngOnInit(): void {
    console.log('EmployerVisibilityToggle directive initialized');
    const sessionUserDetails = sessionStorage.getItem('userDetails');
    const parsedSessionUserData: UserDetailsResponseDTO = sessionUserDetails
      ? JSON.parse(sessionUserDetails)
      : {};

    const userRoleHasModule =
      parsedSessionUserData.userHasApplicationScopeHasUserRole.userRole.userRoleHasModules.find(
        (rhm) => rhm.moduleName === Module.EMPLOYER,
      );

    const roleHasModuleHasComponent =
      userRoleHasModule?.userRoleHasModuleHasComponents.find(
        (rhmhc) => rhmhc.componentName === Component.EMPLOYER,
      );

    const elements =
      roleHasModuleHasComponent?.userRoleHasModuleHasComponentHasElements.map(
        (el) => el.elementName,
      );

    if (this.visibilityId) {
      const isVisible = elements && elements.includes(this.visibilityId);
      if (isVisible) {
        this.renderer.setStyle(this.element.nativeElement, 'display', 'block');
      } else {
        this.renderer.setStyle(this.element.nativeElement, 'display', 'none');
      }
    }
  }
}
