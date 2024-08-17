import { Industry } from '../industry/Industry';

export interface JobPositionResponse {
  id: number;
  name: string;
  industryId: number;
  industry: Industry;
}
