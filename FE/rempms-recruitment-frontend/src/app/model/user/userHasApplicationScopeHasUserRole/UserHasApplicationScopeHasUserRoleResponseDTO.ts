import { RoleHasModuleHasComponentHasElementRequestDTO } from '../hasElement/RoleHasModuleHasComponentHasElementRequestDTO';
import { ApplicationScopeResponseDTO } from '../applicationScope/ApplicationScopeResponseDTO';
import { UserRoleResponseDTO } from '../userRole/UserRoleResponseDTO';

export interface UserHasApplicationScopeHasUserRoleResponseDTO {
  userHasApplicationScopeHasUserRoleId: number;
  applicationScope: ApplicationScopeResponseDTO;
  userRole: UserRoleResponseDTO;
  active: boolean;
}
