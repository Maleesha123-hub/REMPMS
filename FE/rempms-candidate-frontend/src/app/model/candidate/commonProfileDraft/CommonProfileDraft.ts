import { PersonalDetail } from '../personalDetail/PersonalDetail';
import { ProfessionalExperience } from '../professionalExperience/ProfessionalExperience';
import { HigherEducation } from '../higherEducation/HigherEducation';
import { SchoolEducation } from '../schoolEducation/SchoolEducation';
import { Membership } from '../membership/Membership';
import { LanguageProficiency } from '../languageProficiency/LanguageProficiency';
import { Research } from '../research/Research';
import { Achievement } from '../achievments/Achievement';
import { Referee } from '../referee/Referee';
import { FamilyInformation } from '../familyInformation/FamilyInformation';
import { JobPreference } from '../jobPreference/JobPreference';
import { PreferredJobLocation } from '../preferredJobLocation/PreferredJobLocation';
import { DocumentDetails } from '../cvCertificateDocuments/DocumentDetails';

export class CommonProfileDraft {
  id: string | any;
  idCandidate: string | any;
  personalDetail: PersonalDetail | any;
  professionalExperiences: ProfessionalExperience[] | any;
  higherEducations: HigherEducation[] | any;
  schoolEducations: SchoolEducation[] | any;
  memberships: Membership[] | any;
  languageProficiencies: LanguageProficiency[] | any;
  researches: Research[] | any;
  achievements: Achievement | any;
  referees: Referee[] | any;
  familyInformation: FamilyInformation[] | any;
  jobPreferences: JobPreference[] | any;
  preferredJobLocations: PreferredJobLocation[] | any;
  documentDetails: DocumentDetails[] | any;
}
