import { ApplicationScopeResponseDTO } from '../applicationScope/ApplicationScopeResponseDTO';
import { UserRoleHasModuleResponseDTO } from '../userRole/UserRoleHasModuleResponseDTO';

export interface UserRoleResponseDTO {
  userRoleId: number;
  role: string;
  active: boolean;
  applicationScope: ApplicationScopeResponseDTO;
  userRoleHasModules: UserRoleHasModuleResponseDTO[];
}
