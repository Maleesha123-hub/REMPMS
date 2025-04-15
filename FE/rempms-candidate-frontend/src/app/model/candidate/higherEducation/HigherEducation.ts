export class HigherEducation {
  idHigherEducation: string;
  instituteOfStudy: string;
  affiliatedInstitute: string;
  awardType: string; //enum
  awardTypeValue: string; //enum value : for temp table
  idHigherEduQualification: string;
  idAreaOfStudy: string;
  commencedDate: string;
  completionDate: string;
  studentId: string;
  result: string;
  idCountry: string; //location-service
  idLanguage: string; //communication-service //medium
  description: string;

  higherEduQualification: string;
  areaOfStudy: string;

  constructor() {
    this.idHigherEducation = '';
    this.instituteOfStudy = '';
    this.affiliatedInstitute = '';
    this.awardType = '';
    this.idHigherEduQualification = '';
    this.idAreaOfStudy = '';
    this.commencedDate = '';
    this.completionDate = '';
    this.studentId = '';
    this.result = '';
    this.idCountry = '';
    this.idLanguage = '';
    this.description = '';
    this.higherEduQualification = '';
    this.areaOfStudy = '';
    this.awardTypeValue = '';
  }
}
