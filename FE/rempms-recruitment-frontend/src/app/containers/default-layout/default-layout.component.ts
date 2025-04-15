import { Component, OnInit } from '@angular/core';
import { UserDetailsResponseDTO } from '../../model/user/user-details/UserDetailsResponseDTO';

import { navItems } from './_nav';
import { INavData } from '@coreui/angular';

@Component({
  selector: 'app-dashboard',
  templateUrl: './default-layout.component.html',
  styleUrls: ['./default-layout.component.scss'],
})
export class DefaultLayoutComponent implements OnInit {
  public navItems: INavData[] = [];

  constructor() {}

  ngOnInit(): void {
    this.loadNavItems();
  }

  loadNavItems() {
    const sessionUserDetails = sessionStorage.getItem('userDetails');
    const parsedSessionUserData: UserDetailsResponseDTO = sessionUserDetails
      ? JSON.parse(sessionUserDetails)
      : {};

    if (parsedSessionUserData) {
      this.navItems = navItems
        .filter((nav) => {
          return parsedSessionUserData.userHasApplicationScopeHasUserRole.userRole.userRoleHasModules.find(
            (module) => module.moduleName === nav.variant,
          );
        })
        .map((nav) => nav);
    }
  }
}
