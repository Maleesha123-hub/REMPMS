import {Language} from "../language/Language";
import {PreferredCommunication} from "../preferredCommunication/PreferredCommunication";

export class CommunicationInformation {
  idCommunicationInformation: string | any
  phoneNo: string | any
  mobileNo: string | any
  email: string | any
  customerName: string | any
  language: Language | any
  preferredCommunication: PreferredCommunication | any
  commInfoIdLanguage: string | any
  commInfoIdPreferredCommunication: string | any
  commInfoCommonStatus: string | any
}
