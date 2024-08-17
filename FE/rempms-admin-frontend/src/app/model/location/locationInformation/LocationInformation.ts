import {Country} from "../country/Country";
import {City} from "../city/City";
import {District} from "../district/District";
import {Province} from "../province/Province";

export class LocationInformation{
  idLocationInformation: string | any
  locationInformationNo: string | any
  streetNo1: string | any
  streetNo2: string | any
  idCity: string | any
  idDistrict: string | any
  idProvince: string | any
  idCountry: string | any
  country: Country | any
  province: Province | any
  district: District | any
  city: City | any
  commonStatus: string | any
}
