import {Component, OnInit} from '@angular/core';
import {CountryService} from "../../../service/locatiion/country/country.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Country} from "../../../model/location/country/Country";
import Swal from "sweetalert2";

@Component({
  selector: 'app-country',
  templateUrl: './country.component.html',
  styleUrls: ['./country.component.scss']
})
export class CountryComponent implements OnInit {

  countryForm: FormGroup | any;
  country: Country | any;
  countries: Country[] | any;

  constructor(private countryService: CountryService,
              private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    this.getAllActiveCountries();
    this.countryForm = this.formBuilder.group({
      idCountry: [''],
      countryName: ['', Validators.required],
      countryCode: ['', Validators.required],
      nationality: ['', Validators.required],
      commonStatus: ['-1', Validators.required]
    });
  }

  saveUpdateCountry() {
    if (this.countryForm.valid) {
      const countryFormValues = this.countryForm.value;
      this.countryService.saveUpdateCountry(countryFormValues).subscribe(
        (data) => {
          if (data.status == "OK") {
            this.resetCountryForm();
            this.getAllActiveCountries();
            Swal.fire("Saved!", data.message, "success");
          } else {
            this.resetCountryForm();
            this.getAllActiveCountries();
            Swal.fire("Cancelled", data.message, "error");
          }
        },
        (error) => {
          console.log("saveUpdateCountry() >> " + error.get);
        });

    } else {
      this.countryForm.markAllAsTouched();
    }
  }

  getActiveCountryById(idCountry: string) {
    this.countryService.getActiveCountryById(idCountry).subscribe(
      (data) => {
        if (data.data != null) {
          this.country = data.data;
          //Patch values to the form
          this.countryForm.patchValue({
            idCountry: this.country.idCountry,
            countryName: this.country.countryName,
            countryCode: this.country.countryCode,
            nationality: this.country.nationality,
            commonStatus: this.country.commonStatus
          });
        }
      },
      (error) => {
        console.log("getActiveCountryById() >> " + error.get);
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

  deleteCountryById(idCountry: string) {
    this.countryService.deleteCountryById(idCountry).subscribe(
      (data) => {
        if (data.status == "OK") {
          this.resetCountryForm();
          Swal.fire("Deleted!", data.message, "success");
        } else {
          this.resetCountryForm();
          Swal.fire("Cancelled", data.message, "error");
        }
      }, (error) => {
        console.log("deleteCountryById() >> " + error.get);
      });
  }

  resetCountryForm() {
    this.getAllActiveCountries();
    this.countryForm.reset();
    this.countryForm.get('commonStatus')?.patchValue('-1');
  }
}
