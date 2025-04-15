import { UserRoleHasModuleHasComponentResponseDTO } from '../userRole/UserRoleHasModuleHasComponentResponseDTO';

export interface UserRoleHasModuleResponseDTO {
  idUserRoleHasModule: number;
  moduleName: string;
  userRoleHasModuleHasComponents: UserRoleHasModuleHasComponentResponseDTO[];
}
