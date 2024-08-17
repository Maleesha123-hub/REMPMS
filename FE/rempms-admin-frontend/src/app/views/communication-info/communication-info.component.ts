import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Language} from "../../model/communication/language/Language";
import {LanguageService} from "../../service/communication/language/language.service";
import Swal from 'sweetalert2';
import {PreferredCommunication} from "../../model/communication/preferredCommunication/PreferredCommunication";
import {
  PreferredCommunicationMethodService
} from "../../service/communication/preferredCommunicationMethod/preferred-communication-method.service";
import {CommunicationInformation} from "../../model/communication/communicationInformation/CommunicationInformation";
import {
  CommunicationInformationService
} from "../../service/communication/communicationInformation/communication-information.service";


@Component({
  selector: 'app-communication-info',
  templateUrl: './communication-info.component.html',
  styleUrls: ['./communication-info.component.scss']
})
export class CommunicationInfoComponent implements OnInit {
  constructor(private formBuilder: FormBuilder,
              private languageService: LanguageService,
              private preferredCommunicationMethodService: PreferredCommunicationMethodService,
              private communicationInformationService: CommunicationInformationService) {
  }

  languageForm: FormGroup | any;
  prefCommForm: FormGroup | any;
  commInfoForm: FormGroup | any;
  languages: Language[] | any;
  preferredCommunications: PreferredCommunication[] | any;
  communicationInformationList: CommunicationInformation[] | any;
  language: Language | any;
  communicationInformation: CommunicationInformation | any;
  preferredCommunication: PreferredCommunication | any;

  updateButton: boolean = false;

  ngOnInit(): void {

    this.getAllActiveLanguages();
    this.getAllActivePreferredCommunications();
    this.getAllActiveCommunicationInformation();

    this.languageForm = this.formBuilder.group({
      idLanguage: [''],
      languageName: ['', Validators.required],
      languageDescription: ['', Validators.required],
      languageCommonStatus: ['-1', Validators.required]
    });

    this.prefCommForm = this.formBuilder.group({
      idPreferredCommunication: [''],
      preferredCommunicationMethod: ['', Validators.required],
      preferredCommunicationDescription: ['', Validators.required],
      preferredCommunicationCommonStatus: ['-1', Validators.required]
    });

    this.commInfoForm = this.formBuilder.group({
      idCommunicationInformation: [''],
      customerName: ['', Validators.required],
      phoneNo: ['', Validators.required],
      mobileNo: ['', Validators.required],
      email: ['', Validators.required],
      commInfoIdLanguage: ['-1', Validators.required],
      commInfoIdPreferredCommunication: ['-1', Validators.required],
      commInfoCommonStatus: ['-1', Validators.required]
    });
  }

  /**
   * Create, update, delete languages functions
   * @author maleeshasa
   */
  saveUpdateLanguage() {
    if (this.languageForm.valid) {
      const languageFormValues = this.languageForm.value;
      this.languageService.saveUpdateLanguage(languageFormValues).subscribe(
        (data) => {
          if (data.status == "OK") {
            this.resetLanguageForm();
            this.getAllActiveLanguages();
            Swal.fire("Saved!", data.message, "success");
          } else {
            this.resetLanguageForm();
            this.getAllActiveLanguages();
            Swal.fire("Cancelled", data.message, "error");
          }
        },
        (error) => {
          console.log("saveUpdateLanguage() >> " + error.get);
        });

    } else {
      this.languageForm.markAllAsTouched();
    }
  }

  private getAllActiveLanguages() {
    this.languageService.getActiveLanguageList().subscribe(
      (data) => {
        if (data.data != null) {
          this.languages = data.data;
        }
      }, (error) => {
        console.log("getAllActiveLanguages() >> " + error.get);
      });
  }

  getActiveLanguageById(idLanguage: string) {
    this.languageService.getActiveLanguageById(idLanguage).subscribe(
      (data) => {
        if (data.data != null) {
          this.language = data.data;
          //Patch values to the form
          this.languageForm.patchValue({
            idLanguage: this.language.idLanguage,
            languageName: this.language.languageName,
            languageDescription: this.language.languageDescription,
            languageCommonStatus: this.language.languageCommonStatus
          });
        }
      },
      (error) => {
        console.log("getActiveLanguageById() >> " + error.get);
      });
  }

  deleteLanguageById(idLanguage: string) {
    this.languageService.deleteLanguageById(idLanguage).subscribe(
      (data) => {
        if (data.status == "OK") {
          this.getAllActiveLanguages();
          Swal.fire("Deleted!", data.message, "success");
        } else {
          this.getAllActiveLanguages();
          Swal.fire("Cancelled", data.message, "error");
        }
      }, (error) => {
        console.log("deleteLanguageById() >> " + error.get);
      });
  }

  resetLanguageForm() {
    this.languageForm.reset();
    this.languageForm.get('languageCommonStatus')?.patchValue('-1');
  }

  /**
   * Create, update, delete preferred communication functions
   * @author maleeshasa
   */
  saveUpdatePreferredCommunication() {
    if (this.prefCommForm.valid) {
      const prefCommFormValues = this.prefCommForm.value;
      this.preferredCommunicationMethodService.saveUpdatePreferredCommunication(prefCommFormValues).subscribe(
        (data) => {
          if (data.status == "OK") {
            this.resetPrefCommForm();
            this.getAllActivePreferredCommunications();
            Swal.fire("Saved!", data.message, "success");
          } else {
            this.resetPrefCommForm();
            this.getAllActivePreferredCommunications();
            Swal.fire("Cancelled", data.message, "error");
          }
        },
        (error) => {
          console.log("saveUpdatePreferredCommunication() >> " + error.get);
        });

    } else {
      this.prefCommForm.markAllAsTouched();
    }
  }

  private getAllActivePreferredCommunications() {
    this.preferredCommunicationMethodService.getAllActivePreferredCommunications().subscribe(
      (data) => {
        if (data.data != null) {
          this.preferredCommunications = data.data;
        }
      }, (error) => {
        console.log("getAllActivePreferredCommunications() >> " + error.get);
      });
  }

  getActivePreferredCommunicationById(idPreferredCommunication: string) {
    this.preferredCommunicationMethodService.getActivePreferredCommunicationById(idPreferredCommunication).subscribe(
      (data) => {
        if (data.data != null) {
          this.preferredCommunication = data.data;
          //Patch values to the form
          this.prefCommForm.patchValue({
            idPreferredCommunication: this.preferredCommunication.idPreferredCommunication,
            preferredCommunicationMethod: this.preferredCommunication.preferredCommunicationMethod,
            preferredCommunicationDescription: this.preferredCommunication.preferredCommunicationDescription,
            preferredCommunicationCommonStatus: this.preferredCommunication.preferredCommunicationCommonStatus
          });
        }
      },
      (error) => {
        console.log("getActivePreferredCommunicationById() >> " + error.get);
      });
  }

  deletePreferredCommunicationById(idPreferredCommunication: string) {
    this.preferredCommunicationMethodService.deletePreferredCommunicationById(idPreferredCommunication).subscribe(
      (data) => {
        if (data.status == "OK") {
          this.getAllActivePreferredCommunications();
          Swal.fire("Deleted!", data.message, "success");
        } else {
          this.getAllActivePreferredCommunications();
          Swal.fire("Cancelled", data.message, "error");
        }
      }, (error) => {
        console.log("deletePreferredCommunicationById() >> " + error.get);
      });
  }

  resetPrefCommForm() {
    this.getAllActivePreferredCommunications();
    this.prefCommForm.reset();
    this.prefCommForm.get('preferredCommunicationCommonStatus')?.patchValue('-1');
  }

  /**
   * Update, delete communication information functions
   * @author maleeshasa
   */
  saveUpdateCommunicationInformation() {
    if (this.commInfoForm.valid) {
      const commInfoFormValues = this.commInfoForm.value;
      this.communicationInformationService.saveUpdateCommunicationInformation(commInfoFormValues).subscribe(
        (data) => {
          if (data.status == "OK") {
            this.resetCommInfoForm();
            this.getAllActiveCommunicationInformation();
            Swal.fire("Saved!", data.message, "success");
          } else {
            this.resetCommInfoForm();
            this.getAllActiveCommunicationInformation();
            Swal.fire("Cancelled", data.message, "error");
          }
        },
        (error) => {
          console.log("saveUpdateCommunicationInformation() >> " + error.get);
        });

    } else {
      this.commInfoForm.markAllAsTouched();
    }
  }

  private getAllActiveCommunicationInformation() {
    this.communicationInformationService.getAllActiveCommunicationInformation().subscribe(
      (data) => {
        if (data.data != null) {
          this.communicationInformationList = data.data;
        }
      }, (error) => {
        console.log("getAllActiveCommunicationInformation() >> " + error.get);
      });
  }

  getActiveCommunicationInformationById(idCommunicationInformation: string) {
    this.communicationInformationService.getActiveCommunicationInformationById(idCommunicationInformation).subscribe(
      (data) => {
        if (data.data != null) {
          this.communicationInformation = data.data;
          this.updateButton = true;
          //Patch values to the form
          this.commInfoForm.patchValue({
            idCommunicationInformation: this.communicationInformation.idCommunicationInformation,
            customerName: this.communicationInformation.customerName,
            phoneNo: this.communicationInformation.phoneNo,
            mobileNo: this.communicationInformation.mobileNo,
            email: this.communicationInformation.email,
            commInfoIdLanguage: this.communicationInformation.language.idLanguage,
            commInfoIdPreferredCommunication: this.communicationInformation.preferredCommunication.idPreferredCommunication,
            commInfoCommonStatus: this.communicationInformation.commInfoCommonStatus
          });
        }
      },
      (error) => {
        console.log("getActiveCommunicationInformationById() >> " + error.get);
      });
  }

  resetCommInfoForm() {
    this.getAllActiveCommunicationInformation();
    this.updateButton = false;
    this.commInfoForm.reset();
    this.commInfoForm.get('commInfoIdLanguage')?.patchValue('-1');
    this.commInfoForm.get('commInfoIdPreferredCommunication')?.patchValue('-1');
    this.commInfoForm.get('commInfoCommonStatus')?.patchValue('-1');
  }
}
