import {Component, OnInit} from '@angular/core';
import {ProvinceService} from "../../../service/locatiion/province/province.service";
import {CountryService} from "../../../service/locatiion/country/country.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {District} from "../../../model/location/district/District";
import {Country} from "../../../model/location/country/Country";
import {Province} from "../../../model/location/province/Province";
import Swal from "sweetalert2";
import {DistrictService} from "../../../service/locatiion/district/district.service";

@Component({
  selector: 'app-district',
  templateUrl: './district.component.html',
  styleUrls: ['./district.component.scss']
})
export class DistrictComponent implements OnInit {

  districtForm: FormGroup | any;
  district: District | any;
  districts = [] as District[];
  countries = [] as Country[];
  provinces = [] as Province[];

  constructor(private formBuilder: FormBuilder,
              private districtService: DistrictService,
              private provinceService: ProvinceService,
              private countryService: CountryService) {
  }

  ngOnInit(): void {
    this.getAllActiveDistricts();
    this.getAllActiveCountries();
    this.districtForm = this.formBuilder.group({
      idDistrict: [''],
      districtName: ['', Validators.required],
      commonStatus: ['-1', Validators.required],
      idProvince: ['-1', Validators.required],
      idCountry: ['-1', Validators.required]
    });

  }

  // Method to handle the change event of country
  onCountryChange(event: Event) {
    const selectedCountryId = (event.target as HTMLSelectElement).value
    if (selectedCountryId !== '-1') {
      this.getProvincesByCountryId(selectedCountryId);
    }
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

  private getAllActiveDistricts() {
    this.districtService.getAllActiveDistricts().subscribe(
      (data) => {
        if (data.data != null) {
          this.districts = data.data;
        }
      }, (error) => {
        console.log("getAllActiveDistricts() >> " + error.get);
      });
  }

  getProvincesByCountryId(idCountry: string) {
    this.provinceService.getProvincesByCountryId(idCountry).subscribe(
      (data) => {
        if (data.data != null) {
          this.provinces = data.data;
        }
      }, (error) => {
        console.log("getProvincesByCountryId() >> " + error.get);
      });
  }

  saveUpdateDistrict() {
    if (this.districtForm.valid) {
      const districtFormValues = this.districtForm.value;
      this.districtService.saveUpdateDistrict(districtFormValues).subscribe(
        (data) => {
          if (data.status == "OK") {
            this.resetDistrictForm();
            Swal.fire("Saved!", data.message, "success");
          } else {
            this.resetDistrictForm();
            Swal.fire("Cancelled", data.message, "error");
          }
        },
        (error) => {
          console.log("saveUpdateDistrict() >> " + error.get);
        });

    } else {
      this.districtForm.markAllAsTouched();
    }
  }

  getActiveDistrictById(idDistrict: string) {
    this.provinces = [];
    this.districtService.getActiveDistrictById(idDistrict).subscribe(
      (data) => {
        if (data.data != null) {
          this.district = data.data;
          this.provinces.push(this.district.province);
          //Patch values to the form
          this.districtForm.patchValue({
            districtName: this.district.districtName,
            idDistrict: this.district.idDistrict,
            commonStatus: this.district.commonStatus,
            idProvince: this.district.province.idProvince,
            idCountry: this.district.country.idCountry
          });
        }
      },
      (error) => {
        console.log("getActiveDistrictById() >> " + error.get);
      });
  }

  deleteDistrictById(idDistrict: string) {
    this.districtService.deleteDistrictById(idDistrict).subscribe(
      (data) => {
        if (data.status == "OK") {
          this.resetDistrictForm();
          Swal.fire("Deleted!", data.message, "success");
        } else {
          this.resetDistrictForm();
          Swal.fire("Cancelled", data.message, "error");
        }
      }, (error) => {
        console.log("deleteDistrictById() >> " + error.get);
      });
  }

  resetDistrictForm() {
    this.getAllActiveDistricts();
    this.getAllActiveCountries();
    this.districtForm.reset();
    this.districtForm.get('commonStatus')?.patchValue('-1');
    this.districtForm.get('idProvince')?.patchValue('-1');
    this.districtForm.get('idCountry')?.patchValue('-1');
  }
}
