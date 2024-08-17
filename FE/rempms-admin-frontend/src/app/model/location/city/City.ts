import {District} from "../district/District";
import {Province} from "../province/Province";
import {Country} from "../country/Country";

export class City {
  idCity: string | any
  cityName: string | any
  cityZipCode: string | any
  commonStatus: string | any
  idDistrict: string | any
  idProvince: string | any
  idCountry: string | any
  district: District | any
  province: Province |  any
  country: Country |  any
}
