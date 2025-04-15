import { ComponentResponseDTO } from '../component/ComponentResponseDTO';

export interface ModuleResponseDTO {
  id: number;
  name: string;
  active: boolean;
  components: ComponentResponseDTO[];
}
