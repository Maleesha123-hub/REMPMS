import { ModuleResponseDTO } from '../module/ModuleResponseDTO';

export interface ApplicationScopeResponseDTO {
  applicationScopeId: number;
  scope: string;
  active: boolean;
  uniqueId: string;
  modules: ModuleResponseDTO[];
}
