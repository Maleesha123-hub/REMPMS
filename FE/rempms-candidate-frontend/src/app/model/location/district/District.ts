import { Country } from '../country/Country';
import { Province } from '../province/Province';

export class District {
  idDistrict: string | any;
  districtName: string | any;
  commonStatus: string | any;
  idProvince: string | any;
  idCountry: string | any;
  country: Country | any;
  province: Province | any;
}
