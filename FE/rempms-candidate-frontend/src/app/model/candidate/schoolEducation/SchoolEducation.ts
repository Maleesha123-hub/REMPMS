import { SubjectHasSchoolEduGrade } from './SubjectHasSchoolEduGrade';

export class SchoolEducation {
  idSchoolEducation: string | any;

  schoolEduQualification: string | any; //enum : school qualification

  school: string | any; //school/institute

  achievedOn: string | any;

  idLanguage: string | any; //communication-service //medium

  idCountry: string | any; //location-service

  description: string | any;

  idScheme: string | any;

  schemeName: string | any;

  subjectHasSchoolEduGrades: SubjectHasSchoolEduGrade[] | any;
}
