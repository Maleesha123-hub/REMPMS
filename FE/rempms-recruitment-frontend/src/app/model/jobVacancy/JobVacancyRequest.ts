export interface JobVacancyRequest {
  id: number;
  description: string;
  closingDate: string;
  govtJob: boolean;
  walksInInterview: boolean;
  partTime: boolean;
  //posterUrl: string;
  employerId: number;
  jobPositionId: number;
}
