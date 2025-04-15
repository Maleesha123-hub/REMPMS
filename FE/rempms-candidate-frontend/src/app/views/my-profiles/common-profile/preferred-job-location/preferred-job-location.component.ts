import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { CommonProfileDraft } from '../../../../model/candidate/commonProfileDraft/CommonProfileDraft';
import Swal from 'sweetalert2';
import { PreferredJobLocation } from '../../../../model/candidate/preferredJobLocation/PreferredJobLocation';
import { CommonProfileDraftService } from '../../../../service/commonProfileDraft/common-profile-draft.service';
import { LocalStorageService } from '../../../../service/localStorage/local-storage.service';
import { CountryService } from '../../../../service/location/country/country.service';
import { Country } from '../../../../model/location/country/Country';

@Component({
  selector: 'app-preferred-job-location',
  templateUrl: './preferred-job-location.component.html',
  styleUrl: './preferred-job-location.component.scss',
})
export class PreferredJobLocationComponent implements OnInit {
  preferredJobLocationForm: FormGroup | any;
  idCandidate: string | any;
  commonProfileDraft: CommonProfileDraft | any;
  idDraft: string | any;
  countries: Country[] = [];
  selectedLocations: number[] = [];
  files: File[] = [];

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private draftService: CommonProfileDraftService,
    private countryService: CountryService,
  ) {}

  ngOnInit(): void {
    this.getAllActiveCountries();
    this.findDraftByIdCandidate(sessionStorage.getItem('idCandidate') ?? '{}');

    this.preferredJobLocationForm = this.formBuilder.group({});
    this.preferredJobLocationForm.addControl(
      'idPreferredJobLocation',
      new FormControl(''),
    );
  }

  private getAllActiveCountries() {
    this.countryService.getAllActiveCountries().subscribe(
      (data) => {
        if (data.data != null) {
          this.countries = data.data;

          // Dynamically add form controls for each country
          this.countries.forEach((country: Country) => {
            this.preferredJobLocationForm.addControl(
              'jobLocation_' + country.idCountry,
              this.formBuilder.control(false),
            );
          });
        }
      },
      (error) => {
        console.log('getAllActiveCountries() >> ' + error.get);
      },
    );
  }

  createOrModifyCommonProfileDraft() {
    this.countries.forEach((location: Country) => {
      const control = this.preferredJobLocationForm.get(
        'jobLocation_' + location.idCountry,
      );
      if (control && control.value) {
        this.selectedLocations.push(location.idCountry);
      }
    });

    let preferredJobLocations: PreferredJobLocation[] = [];
    this.selectedLocations.forEach((countryId) => {
      const preferredLocation: PreferredJobLocation = {
        idPreferredJobLocation: '',
        idCountry: countryId,
      };
      preferredJobLocations.push(preferredLocation);
    });

    const commonProfileDraft: CommonProfileDraft = {
      id: this.idDraft,
      idCandidate: this.idCandidate,
      personalDetail: null,
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
      preferredJobLocations: preferredJobLocations,
      documentDetails: [],
    };

    this.draftService
      .createOrModifyCommonProfileDraft(commonProfileDraft, this.files)
      .subscribe(
        (data) => {
          if (data.status == 'OK') {
            Swal.fire('Draft saved success.!', data.message, 'success');

            this.findDraftByIdCandidate(
              sessionStorage.getItem('idCandidate') ?? '{}',
            );
          } else {
            Swal.fire('Cancelled', data.message, 'error');
          }
        },
        (error) => {
          console.log('createOrModifyCommonProfileDraft() >> ' + error.get);
        },
      );
  }

  findDraftByIdCandidate(idCandidate: string) {
    this.draftService.findDraftByIdCandidate(idCandidate).subscribe(
      (data) => {
        if (data.data != null) {
          this.commonProfileDraft = data.data;

          this.idDraft = this.commonProfileDraft.id;
          this.idCandidate = this.commonProfileDraft.idCandidate;

          this.commonProfileDraft.preferredJobLocations.forEach(
            (location: PreferredJobLocation) => {
              this.preferredJobLocationForm
                .get('jobLocation_' + location.idCountry)
                .setValue(true);
            },
          );
        }
      },
      (error) => {
        console.log('findDraftByIdCandidate() >> ' + error.get);
      },
    );
  }

  redirectToNext() {
    // Use the Router service to navigate to the 'higher-education' route
    this.router.navigate(['/common-profile/cv-or-certificates']);
  }

  redirectToBack() {
    // Use the Router service to navigate to the 'higher-education' route
    this.router.navigate(['/common-profile/job-preferences']);
  }
}
