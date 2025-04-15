import { CandidateResponse } from '../candidate/CandidateResponse';
import { JobVacancyResponse } from '../jobVacancy/JobVacancyResponse';

export interface RecievedCvsResponse {
  id: number;
  name: string;
  description: string;
  email: string;
  cvUrl: string;
  candidateId: boolean;
  candidate: CandidateResponse;
  jobVacancyId: boolean;
  jobVacancy: JobVacancyResponse;
}
