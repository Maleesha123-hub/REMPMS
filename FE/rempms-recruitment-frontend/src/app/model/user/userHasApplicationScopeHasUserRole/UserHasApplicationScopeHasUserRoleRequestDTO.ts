import { RoleHasModuleHasComponentHasElementRequestDTO } from '../hasElement/RoleHasModuleHasComponentHasElementRequestDTO';

export interface UserHasApplicationScopeHasUserRoleRequestDTO {
  userHasApplicationScopeHasUserRoleId: number;
  applicationScopeId: number;
  userRoleId: number;
  hasElements: RoleHasModuleHasComponentHasElementRequestDTO[];
}
