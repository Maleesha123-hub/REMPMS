import { ComponentElementResponseDTO } from '../componentElement/ComponentElementResponseDTO';

export interface ComponentResponseDTO {
  componentId: number;
  name: string;
  active: boolean;
  componentElements: ComponentElementResponseDTO[];
}
