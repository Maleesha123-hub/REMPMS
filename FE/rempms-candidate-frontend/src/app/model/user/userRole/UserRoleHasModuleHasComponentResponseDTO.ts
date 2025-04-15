import { UserRoleHasModuleHasComponentHasElementResponseDTO } from '../userRole/UserRoleHasModuleHasComponentHasElementResponseDTO';

export interface UserRoleHasModuleHasComponentResponseDTO {
  userRoleHasModuleHasComponentId: number;
  componentName: string;
  userRoleHasModuleHasComponentHasElements: UserRoleHasModuleHasComponentHasElementResponseDTO[];
}
