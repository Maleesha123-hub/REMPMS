import { INavData } from '@coreui/angular';

export const navItems: INavData[] = [
  {
    name: 'Dashboard',
    url: '/dashboard',
    iconComponent: { name: 'cil-speedometer' },
    badge: {
      color: 'info',
      text: 'NEW',
    },
  },
  {
    title: true,
    name: 'Main',
  },
  {
    name: 'Employer',
    url: '/employer',
    iconComponent: { name: 'cil-user-follow' },
  },
  {
    name: 'Job Position',
    url: '/job-position',
    iconComponent: { name: 'cil-puzzle' },
  },
  {
    name: 'Job Vacancy',
    url: '/job-vacancy',
    iconComponent: { name: 'cil-puzzle' },
  },
  {
    name: 'Recieved Cvs',
    url: '/received-cvs',
    iconComponent: { name: 'cil-code' },
  },

  // {
  //   title: true,
  //   name: 'Settings'
  // },
  // {
  //   name: 'Communication.Info',
  //   url: '/communication-info',
  //   iconComponent: {name: 'cil-puzzle'}
  // },
  // {
  //   name: 'Location.Info',
  //   url: '/location-info',
  //   iconComponent: {name: 'cil-location-pin'}
  // },
  // {
  //   name: 'Job Category',
  //   url: '/job-category',
  //   iconComponent: {name: 'cil-puzzle'}
  // },
  // {
  //   name: 'Secret Question',
  //   url: '/',
  //   iconComponent: {name: 'cil-puzzle'}
  // },
];
