export class PersonalDetail {
  idPersonalDetail: string | any;
  salutation: string | any; //enum
  firstName: string | any;
  lastName: string | any;
  initial: string | any;
  gender: string | any; //enum
  dob: string | any;
  maritalStatus: string | any; //enum
  nic: string | any;
  passportNo: string | any;
  expectedSalary: string | any;
  noticePeriod: string | any; //enum

  //location-service
  no: string | any;
  streetNo1: string | any;
  streetNo2: string | any;
  idDistrict: string | any;
  idProvince: string | any;
  idCity: string | any;
  idCountry: string | any;
  idLocationInformation: string | any; //location-service

  //communication-service
  phoneNo: string | any;
  mobileNo: string | any;
  email: string | any;
  idLanguage: string | any;
  idPreferredLanguage: string | any;
  idCommunicationInformation: string | any; //communication-service
}
