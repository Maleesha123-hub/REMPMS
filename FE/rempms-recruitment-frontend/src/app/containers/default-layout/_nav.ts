import { INavData } from '@coreui/angular';

export const navItems: INavData[] = [
  {
    name: 'Dashboard',
    variant: 'recruitment_m_dashboard',
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
    variant: 'recruitment_m_employer',
    url: '/employer',
    iconComponent: { name: 'cil-user-follow' },
  },
  {
    name: 'Job Position',
    variant: 'recruitment_m_job_position',
    url: '/job-position',
    iconComponent: { name: 'cil-puzzle' },
  },
  {
    name: 'Job Vacancy',
    variant: 'recruitment_m_job_vacancy',
    url: '/job-vacancy',
    iconComponent: { name: 'cil-puzzle' },
  },
  {
    name: 'Recieved Cvs',
    variant: 'recruitment_m_recieved_cvs',
    url: '/received-cvs',
    iconComponent: { name: 'cil-code' },
  },
];
