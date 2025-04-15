import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import Swal from 'sweetalert2';
import { CommonProfileDraftService } from '../../../../service/commonProfileDraft/common-profile-draft.service';
import { CommonProfileDraft } from '../../../../model/candidate/commonProfileDraft/CommonProfileDraft';
import { PersonalDetail } from '../../../../model/candidate/personalDetail/PersonalDetail';
import { UserDetailsResponseDTO } from '../../../../model/user/user-details/UserDetailsResponseDTO';
import { CountryService } from '../../../../service/location/country/country.service';
import { Country } from '../../../../model/location/country/Country';
import { Province } from '../../../../model/location/province/Province';
import { ProvinceService } from '../../../../service/location/province/province.service';
import { District } from '../../../../model/location/district/District';
import { DistrictService } from '../../../../service/location/district/district.service';
import { CityService } from '../../../../service/location/city/city.service';
import { City } from '../../../../model/location/city/City';
import { LanguageService } from '../../../../service/communication/language/language.service';
import { HandleErrors } from '../../../../model//errors/handle-errors';
import { selectCommonProfileDraftSaveResponse } from '../store/common-profile.selector';

import { PreferredCommunicationMethodService } from '../../../../service/communication/preferredCommunication/preferred-communication-method.service';
import { PreferredCommunication } from '../../../../model/communication/preferredCommunication/PreferredCommunication';
import { Language } from '../../../../model/communication/language/Language';
import { Observable, Subject, takeUntil } from 'rxjs';
import { select, Store } from '@ngrx/store';
import { CommonProfileState } from '../store/common-profile.reducer';
import * as CommonProfileActions from '../store/common-profile.actions';

@Component({
  selector: 'app-personal-details',
  templateUrl: './personal-details.component.html',
  styleUrl: './personal-details.component.scss',
})
export class PersonalDetailsComponent implements OnInit {
  personalDetailsForm: FormGroup | any;
  country: Country | any;
  countries: Country[] | any;
  province: Province | any;
  provinces: Province[] | any;
  district: District | any;
  districts: District[] | any;
  city: City | any;
  cities: City[] | any;
  preferredCommunications: PreferredCommunication[] | any;
  languages: Language[] | any;
  idCandidate: string | any;
  commonProfileDraft: CommonProfileDraft | any;
  idDraft: string | any;
  files: File[] = [];
  userDetails: UserDetailsResponseDTO | any;
  private unsubscribe$ = new Subject<void>();
  private error$!: Observable<any>;
  // Observables for common profile.
  private selectCommonProfileDraftSavedResponse$!: Observable<any>;

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private commonProfileDraftService: CommonProfileDraftService,
    private countryService: CountryService,
    private provinceService: ProvinceService,
    private districtService: DistrictService,
    private cityService: CityService,
    private languageService: LanguageService,
    private preferredCommunicationMethodService: PreferredCommunicationMethodService,
    private store: Store<{ commonProfileState: CommonProfileState }>,
  ) {}

  ngOnInit(): void {
    const userDetailsString = sessionStorage.getItem('userDetails') ?? '{}'; // Default to an empty JSON object
    this.userDetails = JSON.parse(userDetailsString) as UserDetailsResponseDTO;

    this.storeValue(this.userDetails.idUser as unknown as string);
    this.getAllActiveCountries();
    this.getAllActiveLanguages();
    this.getAllActivePreferredCommunications();
    this.findDraftByIdCandidate(sessionStorage.getItem('idCandidate') ?? '{}');

    this.personalDetailsForm = this.formBuilder.group({
      idPersonalDetail: [''],
      firstName: ['', Validators.required],
      salutation: ['', Validators.required],
      lastName: ['', Validators.required],
      initial: ['', Validators.required],
      gender: ['', Validators.required],
      dob: ['', Validators.required],
      maritalStatus: ['', Validators.required],
      nic: ['', Validators.required],
      passportNo: ['', Validators.required],
      expectedSalary: ['', Validators.required],
      noticePeriod: ['-1', Validators.required],
      no: ['', Validators.required],
      streetNo1: ['', Validators.required],
      streetNo2: ['', Validators.required],
      idDistrict: ['-1', Validators.required],
      idProvince: ['-1', Validators.required],
      idCity: ['-1', Validators.required],
      idCountry: ['-1', Validators.required],
      phoneNo: ['', Validators.required],
      mobileNo: ['', Validators.required],
      email: ['', Validators.required],
      idLanguage: ['-1', Validators.required],
      idPreferredLanguage: ['-1', Validators.required],
    });
  }

  // Method to store a value in session storage
  storeValue(idUser: string): void {
    sessionStorage.setItem('idCandidate', idUser);
  }

  // Method to handle the change event of country
  onCountryChange(event: Event) {
    const selectedCountryId = (event.target as HTMLSelectElement).value;
    this.getAllActiveProvincesByCountry(selectedCountryId);
  }

  // Method to handle the change event of province
  onProvinceChange(event: Event) {
    const selectedCountryId = this.personalDetailsForm.get('idCountry')?.value;
    const selectedProvinceId = (event.target as HTMLSelectElement).value;
    if (selectedCountryId !== '-1' && selectedProvinceId !== '-1') {
      this.getAllActiveDistrictsByCountryAndProvince(
        selectedProvinceId,
        selectedCountryId,
      );
    }
  }

  // Method to handle the change event of district
  onDistrictChange(event: Event) {
    const selectedCountryId = this.personalDetailsForm.get('idCountry')?.value;
    const selectedProvinceId =
      this.personalDetailsForm.get('idProvince')?.value;
    const selectedDistrictId = (event.target as HTMLSelectElement).value;
    if (
      selectedCountryId !== '-1' &&
      selectedProvinceId !== '-1' &&
      selectedDistrictId !== '-1'
    ) {
      this.getAllActiveCitiesByCountryProvinceAndDistrict(
        selectedCountryId,
        selectedProvinceId,
        selectedDistrictId,
      );
    }
  }

  redirectToProfessionalExperience() {
    // Create or modify common profile draft
    this.createOrModifyCommonProfileDraft();
  }

  private getAllActiveCountries() {
    this.countryService.getAllActiveCountries().subscribe(
      (data) => {
        if (data.data != null) {
          this.countries = data.data;
        }
      },
      (error) => {
        console.log('getAllActiveCountries() >> ' + error.get);
      },
    );
  }

  private getAllActiveProvincesByCountry(idCountry: string) {
    this.provinceService.getAllActiveProvincesByCountry(idCountry).subscribe(
      (data) => {
        if (data.data != null) {
          this.provinces = data.data;
        }
      },
      (error) => {
        console.log('getAllActiveProvincesByCountry() >> ' + error.get);
      },
    );
  }

  private getAllActiveDistrictsByCountryAndProvince(
    selectedProvinceId: string,
    selectedCountryId: string,
  ) {
    this.districtService
      .getAllActiveDistrictsByCountryAndProvince(
        selectedProvinceId,
        selectedCountryId,
      )
      .subscribe(
        (data) => {
          if (data.data != null) {
            this.districts = data.data;
          }
        },
        (error) => {
          console.log(
            'getAllActiveDistrictsByCountryAndProvince() >> ' + error.get,
          );
        },
      );
  }

  private getAllActiveCitiesByCountryProvinceAndDistrict(
    selectedCountryId: string,
    selectedProvinceId: string,
    selectedDistrictId: string,
  ) {
    this.cityService
      .getAllActiveCitiesByCountryProvinceAndDistrict(
        selectedCountryId,
        selectedProvinceId,
        selectedDistrictId,
      )
      .subscribe(
        (data) => {
          if (data.data != null) {
            this.cities = data.data;
          }
        },
        (error) => {
          console.log(
            'getAllActiveCitiesByCountryProvinceAndDistrict() >> ' + error.get,
          );
        },
      );
  }

  private getAllActiveLanguages() {
    this.languageService.getAllActiveLanguages().subscribe(
      (data) => {
        if (data.data != null) {
          this.languages = data.data;
        }
      },
      (error) => {
        console.log('getAllActiveLanguages() >> ' + error.get);
      },
    );
  }

  private getAllActivePreferredCommunications() {
    this.preferredCommunicationMethodService
      .getAllActivePreferredCommunications()
      .subscribe(
        (data) => {
          if (data.data != null) {
            this.preferredCommunications = data.data;
          }
        },
        (error) => {
          console.log('getAllActivePreferredCommunications() >> ' + error.get);
        },
      );
  }

  findDraftByIdCandidate(idCandidate: string) {
    this.countries = [];
    this.provinces = [];
    this.districts = [];
    this.cities = [];
    this.preferredCommunications = [];
    this.languages = [];

    this.commonProfileDraftService
      .findDraftByIdCandidate(idCandidate)
      .subscribe(
        (data) => {
          if (data.data != null) {
            this.commonProfileDraft = data.data;

            this.getActiveProvinceById(
              this.commonProfileDraft.personalDetail.idProvince,
            );
            this.getActiveDistrictById(
              this.commonProfileDraft.personalDetail.idDistrict,
            );
            this.getActiveCityById(
              this.commonProfileDraft.personalDetail.idCity,
            );
            this.idDraft = this.commonProfileDraft.id;
            this.idCandidate = this.commonProfileDraft.idCandidate;

            //Patch values to the form
            this.personalDetailsForm.patchValue({
              idPersonalDetail:
                this.commonProfileDraft.personalDetail.idPersonalDetail,
              idCandidate: this.commonProfileDraft.personalDetail.idCandidate,
              firstName: this.commonProfileDraft.personalDetail.firstName,
              salutation: this.commonProfileDraft.personalDetail.salutation,
              lastName: this.commonProfileDraft.personalDetail.lastName,
              initial: this.commonProfileDraft.personalDetail.initial,
              gender: this.commonProfileDraft.personalDetail.gender,
              dob: this.commonProfileDraft.personalDetail.dob,
              maritalStatus:
                this.commonProfileDraft.personalDetail.maritalStatus,
              nic: this.commonProfileDraft.personalDetail.nic,
              passportNo: this.commonProfileDraft.personalDetail.passportNo,
              expectedSalary:
                this.commonProfileDraft.personalDetail.expectedSalary,
              noticePeriod: this.commonProfileDraft.personalDetail.noticePeriod,
              no: this.commonProfileDraft.personalDetail.no,
              streetNo1: this.commonProfileDraft.personalDetail.streetNo1,
              streetNo2: this.commonProfileDraft.personalDetail.streetNo2,
              idDistrict: this.commonProfileDraft.personalDetail.idDistrict,
              idProvince: this.commonProfileDraft.personalDetail.idProvince,
              idCity: this.commonProfileDraft.personalDetail.idCity,
              idCountry: this.commonProfileDraft.personalDetail.idCountry,
              phoneNo: this.commonProfileDraft.personalDetail.phoneNo,
              mobileNo: this.commonProfileDraft.personalDetail.mobileNo,
              email: this.commonProfileDraft.personalDetail.email,
              idLanguage: this.commonProfileDraft.personalDetail.idLanguage,
              idPreferredLanguage:
                this.commonProfileDraft.personalDetail.idPreferredLanguage,
            });
          }
        },
        (error) => {
          console.log('findDraftByIdCandidate() >> ' + error.get);
        },
      );
  }

  getActiveProvinceById(idProvince: string) {
    this.provinceService.getActiveProvinceById(idProvince).subscribe(
      (data) => {
        if (data.data != null) {
          this.province = data.data;
          this.provinces.push(this.province);
        }
      },
      (error) => {
        console.log('getActiveProvinceById() >> ' + error.get);
      },
    );
  }

  getActiveDistrictById(idDistrict: string) {
    this.districtService.getActiveDistrictById(idDistrict).subscribe(
      (data) => {
        if (data.data != null) {
          this.district = data.data;
          this.districts.push(this.district);
        }
      },
      (error) => {
        console.log('getActiveDistrictById() >> ' + error.get);
      },
    );
  }

  getActiveCityById(idCity: string) {
    this.cityService.getActiveCityById(idCity).subscribe(
      (data) => {
        if (data.data != null) {
          this.city = data.data;
          this.cities.push(this.city);
        }
      },
      (error) => {
        console.log('getActiveCityById() >> ' + error.get);
      },
    );
  }

  resetCommonProfileForm() {
    this.personalDetailsForm.reset();
    this.personalDetailsForm.get('idDistrict')?.patchValue('-1');
    this.personalDetailsForm.get('idProvince')?.patchValue('-1');
    this.personalDetailsForm.get('idCity')?.patchValue('-1');
    this.personalDetailsForm.get('idCountry')?.patchValue('-1');
    this.personalDetailsForm.get('idLanguage')?.patchValue('-1');
    this.personalDetailsForm.get('idPreferredLanguage')?.patchValue('-1');
    this.personalDetailsForm.get('noticePeriod')?.patchValue('-1');
  }

  // <------------------------------------------------------ NGRX state manamgent development ------------------------------------------------------>

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  createOrModifyCommonProfileDraft() {
    if (this.personalDetailsForm.valid) {
      const personalDetailsFormValues = this.personalDetailsForm.value;

      // Create an instance of CommonProfileDraft and assign personalDetail
      const commonProfileDraft: CommonProfileDraft = {
        id: this.idDraft,
        idCandidate: sessionStorage.getItem('idCandidate'),
        personalDetail: personalDetailsFormValues as PersonalDetail, // Assuming personalDetailsFormValues has the same structure as PersonalDetail
        professionalExperiences: [],
        higherEducations: [],
        schoolEducations: [],
        memberships: [],
        languageProficiencies: [],
        researches: [],
        achievements: null,
        referees: [],
        familyInformation: [],
        jobPreferences: [],
        preferredJobLocations: null,
        documentDetails: [],
      };

      this.store.dispatch(
        CommonProfileActions.saveCommonProfileData({
          commonProfileDraft: commonProfileDraft,
          documents: this.files,
        }),
      );

      this.selectCommonProfileDraftSavedResponse$ = this.store.select(
        selectCommonProfileDraftSaveResponse,
      );

      this.selectCommonProfileDraftSavedResponse$
        .pipe(takeUntil(this.unsubscribe$))
        .subscribe((state) => {
          if (state.status == 'OK') {
            Swal.fire('Draft saved success.!', state.message, 'success');

            this.resetCommonProfileForm();

            // Use the Router service to navigate to the 'higher-education' route
            this.router.navigate(['/common-profile/professional-experiences']);
          } else {
            this.handleError();
          }
        });

      //   .createOrModifyCommonProfileDraft(commonProfileDraft, this.files)
      //   .subscribe(
      //     (data) => {
      //       if (data.status == 'OK') {
      //         Swal.fire('Draft saved success.!', data.message, 'success');

      //         this.resetCommonProfileForm();

      //         // Use the Router service to navigate to the 'higher-education' route
      //         this.router.navigate([
      //           '/common-profile/professional-experiences',
      //         ]);
      //       } else {
      //         Swal.fire('Cancelled', data.message, 'error');
      //       }
      //     },
      //     (error) => {
      //       console.log('createOrModifyCommonProfileDraft() >> ' + error.get);
      //     }
      //   );
    } else {
      this.personalDetailsForm.markAllAsTouched();
    }
  }

  handleError(): void {
    this.error$ = this.store.pipe(select(selectCommonProfileDraftSaveResponse));
    this.error$.pipe(takeUntil(this.unsubscribe$)).subscribe((state) => {
      if (state) {
        const err: HandleErrors = state;
        // Checking for custom errors by developer
        if (err.error.httpStatus) {
          Swal.fire({
            title: 'Error!',
            text: err.error.message,
            icon: 'error',
            confirmButtonText: 'OK',
          });
          // Checking for default errors
        } else if (err.error.status) {
          Swal.fire({
            title: 'Error!',
            text: err.error.error,
            icon: 'error',
            confirmButtonText: 'OK',
          });
        }
      } else {
        console.log('state not view...');
      }
    });
  }
}
