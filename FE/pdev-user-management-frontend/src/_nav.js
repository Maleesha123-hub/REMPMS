import React from 'react'
import CIcon from '@coreui/icons-react'
import {
  cilUserPlus,
  cilUser,
  cilFingerprint,
  cilSpeedometer,
  cilApplicationsSettings,
  cibOpenAccess,
} from '@coreui/icons'
import { CNavGroup, CNavItem, CNavTitle } from '@coreui/react'

const _nav = [
  {
    component: CNavItem,
    name: 'Dashboard',
    to: '/dashboard',
    icon: <CIcon icon={cilSpeedometer} customClassName="nav-icon" />,
    badge: {
      color: 'info',
      text: 'NEW',
    },
  },
  {
    component: CNavTitle,
    name: 'Main',
  },
  {
    component: CNavItem,
    name: 'User',
    to: '/main/user',
    icon: <CIcon icon={cilUser} customClassName="nav-icon" />,
  },
  {
    component: CNavItem,
    name: 'User Role',
    to: '/settings/user-role',
    icon: <CIcon icon={cilUserPlus} customClassName="nav-icon" />,
  },
  {
    component: CNavTitle,
    name: 'Settings',
  },
  {
    component: CNavGroup,
    name: 'Application Settings',
    to: '/application/permission',
    icon: <CIcon icon={cilApplicationsSettings} customClassName="nav-icon" />,
    items: [
      {
        component: CNavItem,
        name: 'Application Scope',
        to: '/application/scope',
      },
      {
        component: CNavItem,
        name: 'Module',
        to: '/application/module',
      },
      {
        component: CNavItem,
        name: 'Component',
        to: '/application/component',
      },
      {
        component: CNavItem,
        name: 'Component Element',
        to: '/application/component-element',
      },
    ],
  },
  {
    component: CNavGroup,
    name: 'Grant Permission',
    to: '/settings/permission',
    icon: <CIcon icon={cilFingerprint} customClassName="nav-icon" />,
    items: [
      {
        component: CNavItem,
        name: 'Authorize Party',
        to: '/permission/auth-party',
      },
      {
        component: CNavItem,
        name: 'Authorize Party Role',
        to: '/permission/auth-party-role',
      },
      {
        component: CNavItem,
        name: 'Authorize Party Profile',
        to: '/permission/auth-party-profile',
      },
    ],
  },
  {
    component: CNavItem,
    name: 'Access Control',
    to: '/settings/access-control',
    icon: <CIcon icon={cibOpenAccess} customClassName="nav-icon" />,
  },
]

export default _nav
