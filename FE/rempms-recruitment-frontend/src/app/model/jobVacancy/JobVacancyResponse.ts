import { Employer } from '../employer/Employer';
import { JobPositionResponse } from '../jobPosition/JobPositionResponse';

export interface JobVacancyResponse {
  id: number;
  description: string;
  openingDate: string;
  closingDate: string;
  govtJob: boolean;
  walksInInterview: boolean;
  partTime: boolean;
  posterUrl: string;
  employerId: number;
  employer: Employer;
  jobPositionId: number;
  jobPosition: JobPositionResponse;
  jobVacancyRefNo: string;
}
