import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Province} from "../../../model/location/province/Province";
import {ProvinceService} from "../../../service/locatiion/province/province.service";
import Swal from "sweetalert2";
import {Country} from "../../../model/location/country/Country";
import {Observable} from "rxjs";
import {HttpHeaders} from "@angular/common/http";
import {CommonResponse} from "../../../model/commonResponse/CommonResponse";
import {CountryService} from "../../../service/locatiion/country/country.service";

@Component({
  selector: 'app-province',
  templateUrl: './province.component.html',
  styleUrls: ['./province.component.scss']
})
export class ProvinceComponent implements OnInit {

  provinceForm: FormGroup | any;
  province: Province | any;
  provinces = [] as Province[];
  countries = [] as Country[];

  constructor(private formBuilder: FormBuilder,
              private provinceService: ProvinceService,
              private countryService: CountryService) {
  }

  ngOnInit(): void {
    this.getAllActiveCountries();
    this.getAllActiveProvinces();
    this.provinceForm = this.formBuilder.group({
      idProvince: [''],
      provinceName: ['', Validators.required],
      commonStatus: ['-1', Validators.required],
      idCountry: ['-1', Validators.required]
    });
  }

  saveUpdateProvince() {
    if (this.provinceForm.valid) {
      const provinceFormValues = this.provinceForm.value;
      this.provinceService.saveUpdateProvince(provinceFormValues).subscribe(
        (data) => {
          if (data.status == "OK") {
            this.resetProvinceForm();
            Swal.fire("Saved!", data.message, "success");
          } else {
            this.resetProvinceForm();
            Swal.fire("Cancelled", data.message, "error");
          }
        },
        (error) => {
          console.log("saveUpdateProvince() >> " + error.get);
        });

    } else {
      this.provinceForm.markAllAsTouched();
    }
  }

  getActiveProvinceById(idProvince: string) {
    this.provinceService.getActiveProvinceById(idProvince).subscribe(
      (data) => {
        if (data.data != null) {
          this.province = data.data;
          //Patch values to the form
          this.provinceForm.patchValue({
            idProvince: this.province.idProvince,
            provinceName: this.province.provinceName,
            commonStatus: this.province.commonStatus,
            idCountry: this.province.country.idCountry
          });
        }
      },
      (error) => {
        console.log("getActiveProvinceById() >> " + error.get);
      });
  }

  private getAllActiveProvinces() {
    this.provinceService.getAllActiveProvinces().subscribe(
      (data) => {
        if (data.data != null) {
          this.provinces = data.data;
        }
      }, (error) => {
        console.log("getAllActiveProvinces() >> " + error.get);
      });
  }

  deleteProvinceById(idProvince: string) {
    this.provinceService.deleteProvinceById(idProvince).subscribe(
      (data) => {
        if (data.status == "OK") {
          this.resetProvinceForm();
          Swal.fire("Deleted!", data.message, "success");
        } else {
          this.resetProvinceForm();
          Swal.fire("Cancelled", data.message, "error");
        }
      }, (error) => {
        console.log("deleteProvinceById() >> " + error.get);
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

  resetProvinceForm() {
    this.getAllActiveProvinces();
    this.getAllActiveCountries();
    this.provinceForm.reset();
    this.provinceForm.get('commonStatus')?.patchValue('-1');
    this.provinceForm.get('idCountry')?.patchValue('-1');
  }

}
