import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { select, Store } from '@ngrx/store';
import { Observable, Subject, takeUntil } from 'rxjs';
import { RecievedCvsResponse } from 'src/app/model/recievedCvs/RecievedCvsResponse';
import { RecievedCvsState } from '../store/recieved-cvs.reducer';
import {
  selectRecievedCvsDetails,
  selectRecievedCvsErrorResponse,
} from '../store/recieved-cvs.selector';
import { HandleErrors } from 'src/app/model/errors/handle-errors';
import Swal from 'sweetalert2';
import { DocumentService } from 'src/app/service/documentService/document.service';

@Component({
  selector: 'app-cvs-page',
  templateUrl: './cvs-page.component.html',
  styleUrls: ['./cvs-page.component.scss'],
})
export class CvsPageComponent {
  isZipEnable: boolean = true;
  cvsPageForm: FormGroup | any;
  public recievedCvies: RecievedCvsResponse[] = [];
  // Observables for recieved cvs.
  private selectRecievedCvsDetails$!: Observable<any>;
  private unsubscribe$ = new Subject<void>();
  private error$!: Observable<any>;

  constructor(
    private formBuilder: FormBuilder,
    private recivedCvsStore: Store<{ recievedCvsState: RecievedCvsState }>,
    private documentService: DocumentService
  ) {}

  ngOnInit(): void {
    this.onCvsPageFormOnInit();
    this.getRecievedCvsFromStore();
  }

  onCvsPageFormOnInit() {
    this.cvsPageForm = this.formBuilder.group({
      candidateNo: [''],
      verifyStatus: ['-1'],
    });
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  getRecievedCvsFromStore() {
    this.selectRecievedCvsDetails$ = this.recivedCvsStore.select(
      selectRecievedCvsDetails
    );
    this.selectRecievedCvsDetails$
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe((state) => {
        if (state) {
          this.recievedCvies = state.data;
        } else {
          this.handleError();
        }
      });
  }

  displayCvDocByPath(type: string, path: string) {
    let paths: string[] = [];
    paths.push(path);
    this.documentService.downloadImage(type, paths).subscribe(
      (blob) => {
        // Create a URL object from the blob
        const url = window.URL.createObjectURL(blob);

        // Open the URL in a new window
        window.open(url, '_blank');

        // Revoke the object URL after the file has been opened
        setTimeout(() => window.URL.revokeObjectURL(url), 100);
      },
      (error) => {
        Swal.fire({
          title: 'Error!',
          text: 'Document download fail.',
          icon: 'error',
          confirmButtonText: 'OK',
        });
      }
    );
  }

  // Download file through browser
  downloadCvDocByPath(type: string, path: string, name: string) {
    let paths: string[] = [];
    paths.push(path);
    this.documentService.downloadImage(type, paths).subscribe(
      (blob) => {
        // Create a URL object from the blob
        const url = window.URL.createObjectURL(blob);

        // Create an anchor element and set its href to the URL
        const a = document.createElement('a');
        a.href = url;

        // Set the download attribute with a filename
        a.download = name + '_Cv'; // You can set a dynamic filename if needed

        // Append the anchor to the body
        document.body.appendChild(a);

        // Trigger a click event on the anchor
        a.click();

        // Remove the anchor from the document
        document.body.removeChild(a);

        // Revoke the object URL to free up memory
        window.URL.revokeObjectURL(url);
      },
      (error) => {
        Swal.fire({
          title: 'Error!',
          text: 'Document download fail.',
          icon: 'error',
          confirmButtonText: 'OK',
        });
      }
    );
  }

  // Get selected cvs as zip file
  checkedCvUrls: string[] = [];
  onCheckboxChange(checkbox: HTMLInputElement, pathUrl: string) {
    if (checkbox.checked) {
      this.checkedCvUrls.push(pathUrl);
    } else {
      const index = this.checkedCvUrls.indexOf(pathUrl);
      if (index > -1) {
        this.checkedCvUrls.splice(index, 1);
      }
    }
  }

  downloadMultipleCvs() {
    if (this.checkedCvUrls.length > 1) {
      this.documentService.downloadImage('PDF', this.checkedCvUrls).subscribe(
        (blob) => {
          // Create a URL object from the blob
          const url = window.URL.createObjectURL(blob);

          // Create an anchor element and set its href to the URL
          const a = document.createElement('a');
          a.href = url;

          // Set the download attribute with a filename
          a.download = name + 'selectedCvs.zip'; // You can set a dynamic filename if needed

          // Append the anchor to the body
          document.body.appendChild(a);

          // Trigger a click event on the anchor
          a.click();

          // Remove the anchor from the document
          document.body.removeChild(a);

          // Revoke the object URL to free up memory
          window.URL.revokeObjectURL(url);
        },
        (error) => {
          Swal.fire({
            title: 'Error!',
            text: 'Document download fail.',
            icon: 'error',
            confirmButtonText: 'OK',
          });
        }
      );
    }
  }

  onMasterCheckboxChange(masterCheckbox: HTMLInputElement) {
    const checkboxes = document.querySelectorAll(
      'tbody input[type="checkbox"]'
    );
    checkboxes.forEach((element) => {
      const checkbox = element as HTMLInputElement;
      checkbox.checked = masterCheckbox.checked;
    });

    if (masterCheckbox.checked) {
      const list = this.recievedCvies.filter((rc) => rc.cvUrl);
      list.forEach((rcv) => this.checkedCvUrls.push(rcv.cvUrl));
    } else {
      this.checkedCvUrls = [];
    }
  }

  handleError(): void {
    this.error$ = this.recivedCvsStore.pipe(
      select(selectRecievedCvsErrorResponse)
    );
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
