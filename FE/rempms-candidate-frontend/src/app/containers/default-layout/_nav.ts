import {INavData} from '@coreui/angular';

export const navItems: INavData[] = [
  {
    title: true,
    name: 'MAIN'
  },
  {
    name: 'Job Vacancies',
    url: '/job-vacancies',
    iconComponent: {name: 'cil-drop'}
  },
  {
    name: 'My Jobs',
    url: '/my-jobs',
    iconComponent: {name: 'cil-layers'},
    children: [
      {
        name: 'Flagged Jobs',
        url: '/my-jobs/flagged-jobs'
      },
      {
        name: 'Preferred Companies',
        url: '/my-jobs/preferred-companies'
      }
    ]
  },
  {
    name: 'My Profiles',
    url: '/my-profiles',
    iconComponent: {name: 'cil-user'},
    children: [
      {
        name: 'My CV/Document(s)',
        url: '/my-profiles/my-cv-document'
      },
      {
        name: 'Common Profile',
        url: '/common-profile',
        children: [
          {
            name: 'Personal Details',
            url: '/common-profile/personal-details',
            iconComponent: {name: 'cil-speedometer'}
          },
          {
            name: 'Professional Experiences',
            url: '/common-profile/professional-experiences',
            iconComponent: {name: 'cil-speedometer'}
          },
          {
            name: 'Higher Education',
            url: '/common-profile/higher-education',
            iconComponent: {name: 'cil-speedometer'}
          },
          {
            name: 'School Education',
            url: '/common-profile/school-education',
            iconComponent: {name: 'cil-speedometer'}
          },
          {
            name: 'Membership',
            url: '/common-profile/membership',
            iconComponent: {name: 'cil-speedometer'}
          },
          {
            name: 'Language Proficiency',
            url: '/common-profile/language-proficiency',
            iconComponent: {name: 'cil-speedometer'}
          },
          {
            name: 'Research',
            url: '/common-profile/research',
            iconComponent: {name: 'cil-speedometer'}
          },
          {
            name: 'Achievements',
            url: '/common-profile/achievements',
            iconComponent: {name: 'cil-speedometer'}
          },
          {
            name: 'Referees',
            url: '/common-profile/referees',
            iconComponent: {name: 'cil-speedometer'}
          },
          {
            name: 'Family Information',
            url: '/common-profile/family-information',
            iconComponent: {name: 'cil-speedometer'}
          },
          {
            name: 'Job Preference',
            url: '/common-profile/job-preferences',
            iconComponent: {name: 'cil-speedometer'}
          },
          {
            name: 'Preferred Job Location',
            url: '/common-profile/preferred-job-location',
            iconComponent: {name: 'cil-speedometer'}
          },
          {
            name: 'Uploaded CVs/Certificates',
            url: '/common-profile/cv-or-certificates',
            iconComponent: {name: 'cil-speedometer'}
          }
        ]
      },
      {
        name: 'My Login Details',
        url: '/my-profiles/my-login'
      }
    ]
  },
  {
    name: 'My Preferences',
    url: '/my-preferences',
    iconComponent: {name: 'cil-bell'},
    children: [
      {
        name: 'Job Alert(s)',
        url: '/my-preferences/job-alert'
      }
    ]
  },
  // {
  //   name: 'Docs',
  //   url: 'https://coreui.io/angular/docs/templates/installation',
  //   iconComponent: {name: 'cil-description'},
  //   attributes: {target: '_blank', class: '-text-dark'},
  //   class: 'mt-auto'
  // },
  // {
  //   name: 'Try CoreUI PRO',
  //   url: 'https://coreui.io/product/angular-dashboard-template/',
  //   iconComponent: {name: 'cil-layers'},
  //   attributes: {target: '_blank'}
  // }
];
