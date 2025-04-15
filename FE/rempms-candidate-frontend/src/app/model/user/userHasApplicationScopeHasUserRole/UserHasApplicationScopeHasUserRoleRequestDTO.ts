import { RoleHasModuleHasComponentHasElementRequestDTO } from '../hasElement/RoleHasModuleHasComponentHasElementRequestDTO';

export interface UserHasApplicationScopeHasUserRoleRequestDTO {
  userHasApplicationScopeHasUserRoleId: string;
  applicationScopeId: string;
  applicationScope: string;
  userRoleId: string;
  userRole: string;
  hasElements: RoleHasModuleHasComponentHasElementRequestDTO[];
}
