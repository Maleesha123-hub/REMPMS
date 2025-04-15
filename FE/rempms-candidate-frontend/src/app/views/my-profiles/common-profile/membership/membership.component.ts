import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CountryService } from '../../../../service/location/country/country.service';
import { Country } from '../../../../model/location/country/Country';
import { MembershipTypeService } from '../../../../service/membershipType/membership-type.service';
import { MembershipType } from '../../../../model/candidate/membershipType/MembershipType';
import { Membership } from '../../../../model/candidate/membership/Membership';
import { CommonProfileDraft } from '../../../../model/candidate/commonProfileDraft/CommonProfileDraft';
import Swal from 'sweetalert2';
import { CommonProfileDraftService } from '../../../../service/commonProfileDraft/common-profile-draft.service';
import { LocalStorageService } from '../../../../service/localStorage/local-storage.service';

@Component({
  selector: 'app-membership',
  templateUrl: './membership.component.html',
  styleUrl: './membership.component.scss',
})
export class MembershipComponent implements OnInit {
  membershipForm: FormGroup | any;
  countries: Country[] | any;
  membershipTypes: MembershipType[] | any;
  membershipTempList: Membership[] = [];
  tableList: Membership[] = [];
  idCandidate: string | any;
  idDraft: string | any;
  commonProfileDraft: CommonProfileDraft | any;
  emptyListErrorVisible = false;
  showUpdateButton = false;
  showSaveButton = true;
  files: File[] = [];

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private countryService: CountryService,
    private membershipTypeService: MembershipTypeService,
    private draftService: CommonProfileDraftService,
  ) {}

  ngOnInit(): void {
    this.getAllActiveCountries();
    this.getAllMembershipTypes();
    this.findDraftByIdCandidate(sessionStorage.getItem('idCandidate') ?? '{}');

    this.membershipForm = this.formBuilder.group({
      idMembership: [''],
      idMembershipType: ['-1', Validators.required],
      idCountry: ['-1', Validators.required],
      yearObtained: ['', Validators.required],
      description: ['', Validators.required],
    });
  }

  createOrModifyCommonProfileDraft() {
    if (this.membershipForm.valid) {
      if (this.membershipTempList.length == 0) {
        this.emptyListErrorVisible = true;
      } else {
        this.emptyListErrorVisible = false;

        const mergedArray: Membership[] = this.membershipTempList.concat(
          this.tableList,
        );

        // Create an instance of CommonProfileDraft and assign Membership
        const commonProfileDraft: CommonProfileDraft = {
          id: this.idDraft,
          idCandidate: this.idCandidate,
          personalDetail: null,
          professionalExperiences: [],
          higherEducations: [],
          schoolEducations: [], // Assuming MembershipForm has the same structure as Membership
          memberships: mergedArray,
          languageProficiencies: [],
          researches: [],
          achievements: null,
          referees: [],
          familyInformation: [],
          jobPreferences: [],
          preferredJobLocations: null,
          documentDetails: [],
        };

        this.draftService
          .createOrModifyCommonProfileDraft(commonProfileDraft, this.files)
          .subscribe(
            (data) => {
              if (data.status == 'OK') {
                Swal.fire('Draft saved success.!', data.message, 'success');

                this.resetCommonProfileForm();
                this.findDraftByIdCandidate(
                  sessionStorage.getItem('idCandidate') ?? '{}',
                );

                this.showUpdateButton = false;
                this.showSaveButton = true;
              } else {
                Swal.fire('Cancelled', data.message, 'error');
              }
            },
            (error) => {
              console.log('createOrModifyCommonProfileDraft() >> ' + error.get);
            },
          );
      }
    } else {
      this.membershipForm.markAllAsTouched();
    }
  }

  membershipsForTempListTable() {
    if (this.membershipForm.valid) {
      this.emptyListErrorVisible = false;
      const formValue = this.membershipForm.value;

      const selectedMembershipTypeId =
        this.membershipForm.get('idMembershipType')?.value;
      const selectedMembershipType =
        selectedMembershipTypeId != '-1'
          ? this.membershipTypes.find(
              (membershipType: MembershipType) =>
                membershipType.id == selectedMembershipTypeId,
            )
          : null;

      const selectedCountryId = this.membershipForm.get('idCountry')?.value;
      const selectedCountry =
        selectedCountryId != '-1'
          ? this.countries.find(
              (country: Country) => country.idCountry == selectedCountryId,
            )
          : null;

      const membership: Membership = {
        idMembership: formValue.idMembership,
        idMembershipType: formValue.idMembershipType,
        idCountry: formValue.idCountry,
        membershipTypeName:
          selectedMembershipType != null ? selectedMembershipType.name : null,
        countryName:
          selectedCountry != null ? selectedCountry.countryName : null,
        yearObtained: formValue.yearObtained,
        description: formValue.description,
      };

      this.membershipTempList.push(membership);
    } else {
      this.membershipForm.markAllAsTouched();
    }
  }

  deleteMembership(membership: Membership) {
    // Assuming membership is the specific object you want to remove
    this.tableList = this.tableList.filter((item) => item !== membership);

    // Create an instance of CommonProfileDraft and assign SchoolEducation
    const commonProfileDraft: CommonProfileDraft = {
      id: this.idDraft,
      idCandidate: this.idCandidate,
      personalDetail: null,
      professionalExperiences: [],
      higherEducations: [],
      schoolEducations: [],
      memberships: this.tableList.length == 0 ? null : this.tableList,
      languageProficiencies: [],
      researches: [],
      achievements: null,
      referees: [],
      familyInformation: [],
      jobPreferences: [],
      preferredJobLocations: null,
      documentDetails: [],
    };

    this.draftService
      .createOrModifyCommonProfileDraft(commonProfileDraft, this.files)
      .subscribe(
        (data) => {
          if (data.status == 'OK') {
            Swal.fire('Deleted success.!', data.message, 'success');

            this.resetCommonProfileForm();
            this.findDraftByIdCandidate(
              sessionStorage.getItem('idCandidate') ?? '{}',
            );

            this.showUpdateButton = false;
            this.showSaveButton = true;
          } else {
            Swal.fire('Cancelled', data.message, 'error');
          }
        },
        (error) => {
          console.log('deleteMembership() >> ' + error.get);
        },
      );
  }

  findDraftByIdCandidate(idCandidate: string) {
    this.tableList = [];
    this.membershipTempList = [];

    this.draftService.findDraftByIdCandidate(idCandidate).subscribe(
      (data) => {
        if (data.data != null) {
          this.commonProfileDraft = data.data;

          console.log(this.commonProfileDraft);

          this.idDraft = this.commonProfileDraft.id;
          this.idCandidate = this.commonProfileDraft.idCandidate;

          this.commonProfileDraft.memberships.forEach(
            (membership: Membership) => {
              const selectedMembershipType = this.membershipTypes.find(
                (membershipType: MembershipType) =>
                  membershipType.id == membership.idMembershipType,
              );

              const selectedCountry = this.countries.find(
                (country: Country) => country.idCountry == membership.idCountry,
              );

              membership.membershipTypeName = selectedMembershipType.name;
              membership.countryName = selectedCountry.countryName;

              this.tableList.push(membership);
            },
          );
        }
      },
      (error) => {
        console.log('findDraftByIdCandidate() >> ' + error.get);
      },
    );
  }

  private getAllMembershipTypes() {
    this.membershipTypeService.getAllActive().subscribe(
      (data) => {
        if (data.data != null) {
          this.membershipTypes = data.data;
        }
      },
      (error) => {
        console.log('getAllMembershipTypes() >> ' + error.get);
      },
    );
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

  removeTempMembershipItem(index: number) {
    this.membershipTempList.splice(index, 1); // Remove the item at the specified index
  }

  redirectToNext() {
    // Use the Router service to navigate to the 'higher-education' route
    this.router.navigate(['/common-profile/language-proficiency']);
  }

  redirectToBack() {
    // Use the Router service to navigate to the 'higher-education' route
    this.router.navigate(['/common-profile/school-education']);
  }

  resetCommonProfileForm() {
    this.membershipTempList = [];
    this.tableList = [];
    this.membershipForm.reset();
    this.membershipForm.get('idMembershipType')?.patchValue('-1');
    this.membershipForm.get('idCountry')?.patchValue('-1');
  }
}
