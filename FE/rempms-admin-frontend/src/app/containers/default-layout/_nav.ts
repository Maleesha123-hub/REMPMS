import {INavData} from '@coreui/angular';

export const navItems: INavData[] = [
  {
    name: 'Dashboard',
    url: '/dashboard',
    iconComponent: {name: 'cil-speedometer'},
    badge: {
      color: 'info',
      text: 'NEW'
    }
  },
  {
    title: true,
    name: 'Main'
  },
  {
    name: 'User Account',
    url: '/user-account',
    iconComponent: {name: 'cil-user'}
  },
  {
    name: 'User Role',
    url: '/user-role',
    iconComponent: {name: 'cil-user-follow'}
  },
  {
    title: true,
    name: 'Settings'
  },
  {
    name: 'Communication.Info',
    url: '/communication-info',
    iconComponent: {name: 'cil-puzzle'}
  },
  {
    name: 'Location.Info',
    url: '/location-info',
    iconComponent: {name: 'cil-location-pin'}
  },
  {
    name: 'Job Category',
    url: '/job-category',
    iconComponent: {name: 'cil-puzzle'}
  },
  {
    name: 'Secret Question',
    url: '/',
    iconComponent: {name: 'cil-puzzle'}
  },
];
