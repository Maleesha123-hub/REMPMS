import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { DocumentDetails } from '../../../../model/candidate/cvCertificateDocuments/DocumentDetails';
import { DocumentTypeService } from '../../../../service/documentType/document-type.service';
import { DocumentType } from '../../../../model/candidate/cvCertificateDocuments/DocumentType';
import { CommonProfileDraft } from '../../../../model/candidate/commonProfileDraft/CommonProfileDraft';
import Swal from 'sweetalert2';
import { CommonProfileDraftService } from '../../../../service/commonProfileDraft/common-profile-draft.service';
import { CandidateService } from '../../../../service/candidate/candidate.service';
import { LocalStorageService } from '../../../../service/localStorage/local-storage.service';
import { CommonResponse } from 'src/app/model/commonResponse/CommonResponse';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-cv-or-certificates',
  templateUrl: './cv-or-certificates.component.html',
  styleUrl: './cv-or-certificates.component.scss',
})
export class CvOrCertificatesComponent implements OnInit {
  documentDetailsForm: FormGroup | any;
  files: File[] = [];
  tempList: DocumentDetails[] = []; // Initialize tempList as an empty array
  tableList: DocumentDetails[] = []; // Initialize tableList as an empty array
  emptyListErrorVisible = false;
  documentTypes: DocumentType[] = [];
  actualFileName: string = '';
  idCandidate: string | any;
  idDraft: string | any;
  commonProfileDraft: CommonProfileDraft | any;

  imageSrc: any = 'assets/img/default_image.jpg';
  @ViewChild('fileInput') fileInputRef!: ElementRef;

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private documentTypeService: DocumentTypeService,
    private draftService: CommonProfileDraftService,
    private candidateService: CandidateService,
  ) {}

  ngOnInit(): void {
    this.findDraftByIdCandidate(sessionStorage.getItem('idCandidate') ?? '{}');
    this.getAllActiveDocumentTypes();

    this.documentDetailsForm = this.formBuilder.group({
      documentId: [''],
      documentTypeId: ['-1'],
      document: [''],
    });
  }

  async createOrModifyCommonProfileDraft() {
    if (this.tempList.length == 0) {
      this.emptyListErrorVisible = true;
    } else {
      this.emptyListErrorVisible = false;

      const fileInput = <HTMLInputElement>document.getElementById('document');

      if (fileInput.files && fileInput.files.length > 0) {
        this.files.push(fileInput.files[0]);
      }

      const mergedArray: DocumentDetails[] = this.tempList;

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
        preferredJobLocations: null,
        documentDetails: mergedArray,
      };

      try {
        const data = await firstValueFrom(
          this.draftService.createOrModifyCommonProfileDraft(
            commonProfileDraft,
            this.files,
          ),
        );

        if (data.status == 'OK') {
          // Step 1: Execute findDraftByIdCandidate function and wait for it to finish
          await this.findDraftByIdCandidate(
            sessionStorage.getItem('idCandidate') ?? '{}',
          );

          // Step 2: Execute saveCandidate function and wait for it to finish
          await this.saveCandidate(this.commonProfileDraft.idCandidate);
          await this.resetDocumentForm();
        } else {
          Swal.fire('Cancelled', data.message, 'error');
        }
      } catch (error) {
        console.log('createOrModifyCommonProfileDraft() >> ' + error);
        Swal.fire(
          'Error',
          'An error occurred while saving the draft.',
          'error',
        );
      }
    }
  }

  // TODO: create candidate in candidate service
  saveCandidate(id: number) {
    this.candidateService.saveCandidate(id).subscribe((data) => {
      try {
        if (data.status == 'CREATED') {
          Swal.fire(
            'Saved success.! \n Candidate No: ' + data.data.candidateNo,
            data.message,
            'success',
          );
        } else {
          Swal.fire('Save failed.!', data.message, 'error');
        }
      } catch (error: any) {
        console.log('saveCandidate() >> ' + error.message);
        Swal.fire(
          'Error',
          'An error occurred while saving the candidate.',
          'error',
        );
      }
    });
  }

  findDraftByIdCandidate(idCandidate: string) {
    this.getAllActiveDocumentTypes();
    this.tableList = [];
    this.tempList = [];

    this.draftService.findDraftByIdCandidate(idCandidate).subscribe(
      (data) => {
        if (data.data != null) {
          this.commonProfileDraft = data.data;

          this.idDraft = this.commonProfileDraft.id;
          this.idCandidate = this.commonProfileDraft.idCandidate;

          this.commonProfileDraft.documentDetails.forEach(
            (documentDetails: DocumentDetails) => {
              const docType = this.documentTypes.find(
                (dt) => dt.id === documentDetails.documentTypeId,
              );
              documentDetails.documentType =
                docType != undefined ? docType.name : '-';

              this.tableList.push(documentDetails);
            },
          );
        }
      },
      (error) => {
        console.log('findDraftByIdCandidate() >> ' + error.get);
      },
    );
  }

  documentTempListTable() {
    this.emptyListErrorVisible = false;
    const formValue = this.documentDetailsForm.value;

    const fileInput = <HTMLInputElement>document.getElementById('document');
    let file = null;

    if (fileInput.files && fileInput.files.length > 0) {
      file = fileInput.files[0];
      this.actualFileName = file.name;
    }

    const docType = this.documentTypes.find(
      (dt) => dt.id === formValue.documentTypeId,
    );

    // temp list array
    const documentDetails: DocumentDetails = {
      id: '',
      documentTypeId: formValue.documentTypeId,
      documentType: docType != undefined ? docType.name : '-',
      actualFileName: this.actualFileName,
      file: null,
      folderType: '',
      refNo: '',
    };

    this.tempList.push(documentDetails);
  }

  removeTempItem(index: number) {
    this.tempList.splice(index, 1); // Remove the item at the specified index
  }

  private getAllActiveDocumentTypes() {
    this.documentTypeService.getAllActiveDocumentTypes().subscribe(
      (data) => {
        if (data.data != null) {
          this.documentTypes = data.data;
        }
      },
      (error) => {
        console.log('getAllActiveDocumentTypes() >> ' + error.get);
      },
    );
  }

  previewImage() {
    const fileInputElement = this.fileInputRef
      .nativeElement as HTMLInputElement;
    const file = fileInputElement.files?.[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = () => {
        this.imageSrc = reader.result;
      };
      reader.readAsDataURL(file);
    }
  }

  getDocumentById(document: DocumentDetails) {
    // Convert Base64 to Blob
    this.documentDetailsForm.patchValue({
      documentId: document.id,
      documentTypeId: document.documentTypeId,
    });

    const base64Data = document.file.data;
    // Assuming it's an image (JPEG)
    const mimeType = 'image/jpeg'; // Adjust accordingly if it's a different type (e.g., "application/pdf")
    const blob = this.base64ToBlob(base64Data, mimeType);
    this.displayImage(blob);
  }

  // Display image in the place holder
  displayImage(blob: Blob) {
    const reader = new FileReader();
    reader.onload = () => {
      this.imageSrc = reader.result;
    };
    reader.readAsDataURL(blob);
  }

  base64ToBlob(base64: string, mimeType: string) {
    const byteCharacters = atob(base64);
    const byteNumbers = new Array(byteCharacters.length);

    for (let i = 0; i < byteCharacters.length; i++) {
      byteNumbers[i] = byteCharacters.charCodeAt(i);
    }

    const byteArray = new Uint8Array(byteNumbers);
    return new Blob([byteArray], { type: mimeType });
  }

  redirectToBack() {
    // Use the Router service to navigate to the 'preferred-job-location' route
    this.router.navigate(['/common-profile/preferred-job-location']);
  }

  resetDocumentForm() {
    this.tableList = [];
    this.tempList = [];
    this.documentDetailsForm.reset();
    this.imageSrc = 'assets/img/default_image.jpg';
    this.documentDetailsForm.get('documentTypeId')?.patchValue('-1');
    this.fileInputRef.nativeElement.value = '';
  }
}
