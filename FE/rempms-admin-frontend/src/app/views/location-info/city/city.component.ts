import {Component, OnInit} from '@angular/core';
import {CountryService} from "../../../service/locatiion/country/country.service";
import {ProvinceService} from "../../../service/locatiion/province/province.service";
import {DistrictService} from "../../../service/locatiion/district/district.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {City} from "../../../model/location/city/City";
import {Country} from "../../../model/location/country/Country";
import {Province} from "../../../model/location/province/Province";
import {District} from "../../../model/location/district/District";
import Swal from "sweetalert2";
import {CityService} from "../../../service/locatiion/city/city.service";

@Component({
  selector: 'app-city',
  templateUrl: './city.component.html',
  styleUrls: ['./city.component.scss']
})
export class CityComponent implements OnInit {

  cityForm: FormGroup | any;
  city: City | any;
  cities = [] as City[];
  countries = [] as Country[];
  provinces = [] as Province[];
  districts = [] as District[];

  constructor(private formBuilder: FormBuilder,
              private cityService: CityService,
              private countryService: CountryService,
              private provinceService: ProvinceService,
              private districtService: DistrictService) {
  }

  ngOnInit(): void {
    this.getAllActiveCountries();
    this.getAllActiveCities();

    this.cityForm = this.formBuilder.group({
      idCity: [''],
      cityName: ['', Validators.required],
      cityZipCode: ['', Validators.required],
      idCountry: ['-1', Validators.required],
      idProvince: ['-1', Validators.required],
      idDistrict: ['-1', Validators.required],
      commonStatus: ['-1', Validators.required]
    });

  }

  // Method to handle the change event of country
  onCountryChange(event: Event) {
    const selectedCountryId = (event.target as HTMLSelectElement).value;
    this.getProvincesByCountryId(selectedCountryId);
  }

  // Method to handle the change event of province
  onProvinceChange(event: Event) {
    const selectedCountryId = this.cityForm.get('idCountry')?.value;
    const selectedProvinceId = (event.target as HTMLSelectElement).value;
    if (selectedCountryId !== '-1' && selectedProvinceId !== '-1') {
      this.getDistrictsByCountryIdAndProvinceId(selectedProvinceId, selectedCountryId);
    }
  }

  saveUpdateCity() {
    if (this.cityForm.valid) {
      const cityFormValues = this.cityForm.value;
      this.cityService.saveUpdateCity(cityFormValues).subscribe(
        (data) => {
          if (data.status == "OK") {
            this.resetCityForm();
            Swal.fire("Saved!", data.message, "success");
          } else {
            this.resetCityForm();
            Swal.fire("Cancelled", data.message, "error");
          }
        },
        (error) => {
          console.log("saveUpdateCity() >> " + error.get);
        });

    } else {
      this.cityForm.markAllAsTouched();
    }
  }

  private getAllActiveCities() {
    this.cityService.getAllActiveCities().subscribe(
      (data) => {
        if (data.data != null) {

          this.cities = data.data;
        }
      }, (error) => {
        console.log("getAllActiveCities() >> " + error.get);
      });
  }

  getActiveCityById(idCity: string) {
    this.provinces = [];
    this.districts = [];
    this.cityService.getActiveCityById(idCity).subscribe(
      (data) => {
        if (data.data != null) {
          this.city = data.data;
          this.provinces.push(this.city.province);
          this.districts.push(this.city.district);
          //Patch values to the form
          this.cityForm.patchValue({
            idCity: this.city.idCity,
            cityName: this.city.cityName,
            cityZipCode: this.city.cityZipCode,
            idCountry: this.city.country.idCountry,
            idProvince: this.city.province.idProvince,
            idDistrict: this.city.district.idDistrict,
            commonStatus: this.city.commonStatus
          });
        }
      },
      (error) => {
        console.log("getActiveCityById() >> " + error.get);
      });
  }

  deleteCityById(idCity: string) {
    this.cityService.deleteCityById(idCity).subscribe(
      (data) => {
        if (data.status == "OK") {
          this.resetCityForm();
          Swal.fire("Deleted!", data.message, "success");
        } else {
          this.resetCityForm();
          Swal.fire("Cancelled", data.message, "error");
        }
      }, (error) => {
        console.log("deleteCityById() >> " + error.get);
      });
  }

  private getAllActiveCountries() {
    this.countryService.getAllActiveCountries().subscribe(
      (data) => {
        if (data.data != null) {
          this.countries = data.data;
        }
      }, (error) => {
        console.log("getAllActiveCountries() >> " + error.get);
      });
  }

  private getProvincesByCountryId(idCountry: string) {
    this.provinceService.getProvincesByCountryId(idCountry).subscribe(
      (data) => {
        if (data.data != null) {
          this.cityForm.get('idProvince')?.patchValue('-1');
          this.provinces = data.data;
        }
      }, (error) => {
        console.log("getProvincesByCountryId() >> " + error.get);
      });
  }

  private getDistrictsByCountryIdAndProvinceId(selectedProvinceId: string, selectedCountryId: string) {
    this.districtService.getDistrictsByCountryIdAndProvinceId(selectedProvinceId, selectedCountryId).subscribe(
      (data) => {
        if (data.data != null) {
          this.cityForm.get('idDistrict')?.patchValue('-1');
          this.districts = data.data;
        }
      }, (error) => {
        console.log("getDistrictsByCountryIdAndProvinceId() >> " + error.get);
      });
  }

  resetCityForm() {
    this.getAllActiveCountries();
    this.getAllActiveCities();
    this.cityForm.reset();
    this.cityForm.get('idCountry')?.patchValue('-1');
    this.cityForm.get('idProvince')?.patchValue('-1');
    this.cityForm.get('idDistrict')?.patchValue('-1');
    this.cityForm.get('commonStatus')?.patchValue('-1');
  }
}
