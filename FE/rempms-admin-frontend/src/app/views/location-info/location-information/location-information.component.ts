import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CountryService} from "../../../service/locatiion/country/country.service";
import {DistrictService} from "../../../service/locatiion/district/district.service";
import {ProvinceService} from "../../../service/locatiion/province/province.service";
import {CityService} from "../../../service/locatiion/city/city.service";
import {Province} from "../../../model/location/province/Province";
import {Country} from "../../../model/location/country/Country";
import {District} from "../../../model/location/district/District";
import {City} from "../../../model/location/city/City";
import {LocationInformation} from "../../../model/location/locationInformation/LocationInformation";
import {LocationInformationService} from "../../../service/locatiion/locationInformation/location-information.service";
import Swal from "sweetalert2";

@Component({
  selector: 'app-location-information',
  templateUrl: './location-information.component.html',
  styleUrls: ['./location-information.component.scss']
})
export class LocationInformationComponent implements OnInit {

  locationInformationForm: FormGroup | any;
  locationInformation: LocationInformation | any;
  locationInfos = [] as LocationInformation[];
  countries = [] as Country[];
  provinces = [] as Province[];
  districts = [] as District[];
  cities = [] as City[];

  constructor(private formBuilder: FormBuilder,
              private countryService: CountryService,
              private provinceService: ProvinceService,
              private districtService: DistrictService,
              private cityService: CityService,
              private locationInfoService: LocationInformationService) {
  }

  updateButton: boolean = false;

  ngOnInit(): void {
    this.getAllActiveCountries();
    this.getAllActiveLocationInformation();
    this.locationInformationForm = this.formBuilder.group({
      idLocationInformation: [''],
      locationInformationNo: ['', Validators.required],
      streetNo1: ['', Validators.required],
      streetNo2: ['', Validators.required],
      idCountry: ['-1', Validators.required],
      idProvince: ['-1', Validators.required],
      idDistrict: ['-1', Validators.required],
      idCity: ['-1', Validators.required],
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
    const selectedCountryId = this.locationInformationForm.get('idCountry')?.value;
    const selectedProvinceId = (event.target as HTMLSelectElement).value;
    if (selectedCountryId !== '-1' && selectedProvinceId !== '-1') {
      this.getDistrictsByCountryIdAndProvinceId(selectedProvinceId, selectedCountryId);
    }
  }

  // Method to handle the change event of district
  onDistrictChange(event: Event) {
    const selectedCountryId = this.locationInformationForm.get('idCountry')?.value;
    const selectedProvinceId = this.locationInformationForm.get('idProvince')?.value;
    const selectedDistrictId = (event.target as HTMLSelectElement).value;
    if (selectedCountryId !== '-1' && selectedProvinceId !== '-1' && selectedDistrictId !== '-1') {
      this.getCitiesByIdCountryProvinceAndDistrict(selectedCountryId, selectedProvinceId, selectedDistrictId);
    }
  }

  saveUpdateLocationInfo() {
    if (this.locationInformationForm.valid) {
      const locationInformationFormValues = this.locationInformationForm.value;
      this.locationInfoService.saveUpdateLocationInfo(locationInformationFormValues).subscribe(
        (data) => {
          if (data.status == "OK") {
            this.resetLocationInformationForm();
            Swal.fire("Saved!", data.message, "success");
          } else {
            this.resetLocationInformationForm();
            Swal.fire("Cancelled", data.message, "error");
          }
        },
        (error) => {
          console.log("saveUpdateLocationInfo() >> " + error.get);
        });

    } else {
      this.locationInformationForm.markAllAsTouched();
    }
  }

  getActiveLocationInformationById(idLocationInformation: string) {
    this.countries = [];
    this.provinces = [];
    this.districts = [];
    this.cities = [];
    this.locationInfoService.getActiveLocationInformationById(idLocationInformation).subscribe(
      (data) => {
        if (data.data != null) {
          this.locationInformation = data.data;
          this.updateButton = true;
          this.countries.push(this.locationInformation.country);
          this.provinces.push(this.locationInformation.province);
          this.districts.push(this.locationInformation.district);
          this.cities.push(this.locationInformation.city);
          //Patch values to the form
          this.locationInformationForm.patchValue({
            idLocationInformation: this.locationInformation.idLocationInformation,
            locationInformationNo: this.locationInformation.locationInformationNo,
            streetNo1: this.locationInformation.streetNo1,
            streetNo2: this.locationInformation.streetNo2,
            idCountry: this.locationInformation.country.idCountry,
            idProvince: this.locationInformation.province.idProvince,
            idDistrict: this.locationInformation.district.idDistrict,
            idCity: this.locationInformation.city.idCity,
            commonStatus: this.locationInformation.commonStatus
          });
        }
      },
      (error) => {
        console.log("getActiveLocationInformationById() >> " + error.get);
      });
  }

  private getAllActiveLocationInformation() {
    this.locationInfoService.getAllActiveLocationInformation().subscribe(
      (data) => {
        if (data.data != null) {
          this.locationInfos = data.data;
        }
      }, (error) => {
        console.log("getAllActiveLocationInformation() >> " + error.get);
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
          this.districts = data.data;
        }
      }, (error) => {
        console.log("getDistrictsByCountryIdAndProvinceId() >> " + error.get);
      });
  }

  private getCitiesByIdCountryProvinceAndDistrict(selectedCountryId: string, selectedProvinceId: string, selectedDistrictId: string) {
    this.cityService.getByIdCountryProvinceAndDistrict(selectedCountryId, selectedProvinceId, selectedDistrictId).subscribe(
      (data) => {
        if (data.data != null) {
          this.cities = data.data;
        }
      }, (error) => {
        console.log("getByIdCountryProvinceAndDistrict() >> " + error.get);
      });
  }

  deleteLocationInformationById(idLocationInformation: string) {
    this.locationInfoService.deleteLocationInformationById(idLocationInformation).subscribe(
      (data) => {
        if (data.status == "OK") {
          this.resetLocationInformationForm();
          Swal.fire("Deleted!", data.message, "success");
        } else {
          this.resetLocationInformationForm();
          Swal.fire("Cancelled", data.message, "error");
        }
      }, (error) => {
        console.log("deleteLocationInformationById() >> " + error.get);
      });
  }

  resetLocationInformationForm() {
    this.getAllActiveLocationInformation();
    this.updateButton = false;
    this.locationInformationForm.reset();
    this.locationInformationForm.get('idCountry')?.patchValue('-1');
    this.locationInformationForm.get('idProvince')?.patchValue('-1');
    this.locationInformationForm.get('idDistrict')?.patchValue('-1');
    this.locationInformationForm.get('idCity')?.patchValue('-1');
    this.locationInformationForm.get('commonStatus')?.patchValue('-1');
  }
}
