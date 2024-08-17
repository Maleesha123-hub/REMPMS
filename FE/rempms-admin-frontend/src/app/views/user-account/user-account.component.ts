import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {UserRoleService} from "../../service/userRole/user-role.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserAccount} from "../../model/userAccount/UserAccount";
import {UserAccountService} from "../../service/userAccount/user-account.service";
import Swal from "sweetalert2";
import {UserRole} from "../../model/userRole/UserRole";
import {SyncDataService} from "../../service/syncData/sync-data.service";

@Component({
  selector: 'app-user-account',
  templateUrl: './user-account.component.html',
  styleUrls: ['./user-account.component.scss']
})
export class UserAccountComponent implements OnInit {

  imageSrc: any = 'assets/img/user.png';
  @ViewChild('fileInput') fileInputRef!: ElementRef;
  isEditMode: Boolean | any;
  userAccountForm: FormGroup | any;
  userAccount: UserAccount | any;
  userAccounts: UserAccount[] | any;
  userRoles: UserRole[] | any;

  constructor(private userRoleService: UserRoleService,
              private userAccountService: UserAccountService,
              private syncDataService: SyncDataService,
              private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    this.getAllActiveUserAccounts();
    this.getAllActiveUserRoles();
    this.userAccountForm = this.formBuilder.group({
      idUserAccount: [''],
      hiddenUserImageName: [''],
      userName: ['', Validators.required],
      password: ['', Validators.required],
      confirmPassword: ['', Validators.required],
      userImage: [''],
      idUserRole: ['-1', Validators.required],
      commonStatus: ['-1', Validators.required]
    });

  }

  previewImage() {
    const fileInputElement = this.fileInputRef.nativeElement as HTMLInputElement;
    const userImage = fileInputElement.files?.[0];
    if (this.isValidImage(userImage) && this.isValidExtensionImage(userImage)) {
      const reader = new FileReader();
      reader.onload = () => {
        this.imageSrc = reader.result as string;
      };
      // @ts-ignore
      reader.readAsDataURL(userImage);
    }
  }

  uploadUserImage() {
    const fileInputElement = this.fileInputRef.nativeElement as HTMLInputElement;
    const userImage = fileInputElement.files?.[0];
    if (userImage == null) {
      if (this.isEditMode == true) {
        const imageName = this.userAccountForm.get('hiddenUserImageName')?.value;
        if (imageName.isEmpty) {
          Swal.fire("File Not Found", "Please Upload an Image.", "error");
        } else {
          this.saveUpdateUserAccount(imageName);
        }
      } else {
        Swal.fire("File Not Found", "Please Upload an Image.", "error");
      }
    } else if (this.isValidImage(userImage) && this.isValidExtensionImage(userImage)) {
      let formData = new FormData();
      formData.append("file", userImage);
      formData.append("id", this.userAccountForm.get('idUserAccount')?.value);
      formData.append("docType", 'USER_IMG');
      this.upload(formData);
    }
  }

  private upload(formData: FormData) {
    this.userAccountService.uploadUserImage(formData).subscribe(
      (data: any) => {
        if (data.status == "OK") {
          this.saveUpdateUserAccount(data.data[0]);
        } else {
          Swal.fire("Cancelled", data.message, "error");
        }
      }, (error) => {
        console.log("upload() >> " + error.get);
      });
  }

  // @ts-ignore
  private isValidImage(userImage: any) {
    if (userImage) {
      const maxSizeInBytes = 10 * 1024 * 1024; //10 MB
      if (userImage.size > maxSizeInBytes) {
        Swal.fire("Invalid file", "File is too large.", "error");
      } else {
        return true;
      }
    }
  }

  // @ts-ignore
  private isValidExtensionImage(userImage: any) {
    if (userImage) {
      const allowedExtensions = ['jpg', 'jpeg', 'png'];
      const fileExtension = userImage.name.split('.').pop()?.toLowerCase();
      if (!allowedExtensions.includes(<string>fileExtension)) {
        Swal.fire("Invalid file", "Invalid file type.", "error");
      } else {
        return true;
      }
    }
  }

  saveUpdateUserAccount(image: string) {
    if (this.userAccountForm.valid) {
      this.userAccountForm.patchValue({hiddenUserImageName: image});
      const userAccountFormValues = this.userAccountForm.value;
      this.userAccountService.saveUpdateUserAccount(userAccountFormValues).subscribe(
        (data) => {
          if (data.status == "OK") {
            this.resetUserAccountForm();
            Swal.fire("Saved!", data.message, "success");
          } else {
            this.resetUserAccountForm();
            Swal.fire("Cancelled", data.message, "error");
          }
        },
        (error) => {
          console.log("saveUpdateUserAccount() >> " + error.get);
        });

    } else {
      this.userAccountForm.markAllAsTouched();
    }
  }

  getActiveUserAccountById(idUserAccount: any) {
    this.isEditMode = true;
    this.userAccountService.getActiveUserAccountById(idUserAccount).subscribe(
      (data) => {
        if (data.data != null) {
          this.userAccount = data.data;
          this.userRoles.push(this.userAccount.userRole);
          //Patch values to the form
          this.userAccountForm.patchValue({
            idUserAccount: this.userAccount.idUserAccount,
            userName: this.userAccount.userName,
            hiddenUserImageName: this.userAccount.hiddenUserImageName,
            idUserRole: this.userAccount.userRole.idUserRole,
            commonStatus: this.userAccount.commonStatus
          });
          this.setUserImage(this.userAccount.idUserAccount, this.userAccount.hiddenUserImageName);
        }
      },
      (error) => {
        console.log("getActiveDistrictById() >> " + error.get);
      });
  }

  private setUserImage(idUserAccount: string, hiddenUserImageName: string) {
    this.userAccountService.setUserImage(idUserAccount, hiddenUserImageName).subscribe(
      (data) => {
        const reader = new FileReader();
        reader.onload = () => {
          this.imageSrc = reader.result;
        };
        reader.readAsDataURL(data);
      }, (error) => {
        console.log("setUserImage() >> " + error.get);
      });
  }

  public syncData() {
    this.syncDataService.SyncUserData().subscribe(
      (data) => {
        if (data.status == "OK") {
          this.resetUserAccountForm();
          Swal.fire("Synced!", data.message, "success");
        } else {
          this.resetUserAccountForm();
          Swal.fire("Synced unsuccessful", data.message, "error");
        }
      }, (error) => {
        console.log("syncData() >> " + error.get);
      });
  }

  deleteUserAccountById(idUserAccount: any) {
    this.userAccountService.deleteUserAccountById(idUserAccount).subscribe(
      (data) => {
        if (data.status == "OK") {
          this.resetUserAccountForm();
          Swal.fire("Deleted!", data.message, "success");
        } else {
          this.resetUserAccountForm();
          Swal.fire("Cancelled", data.message, "error");
        }
      }, (error) => {
        console.log("deleteUserAccountById() >> " + error.get);
      });
  }

  private getAllActiveUserAccounts() {
    this.userAccountService.getAllActiveUserAccounts().subscribe(
      (data) => {
        if (data.data != null) {
          this.userAccounts = data.data;
        }
      }, (error) => {
        console.log("getAllActiveUserAccounts() >> " + error.get);
      });
  }

  private getAllActiveUserRoles() {
    this.userRoleService.getAllActiveUserRoles().subscribe(
      (data) => {
        if (data.data != null) {
          this.userRoles = data.data;
        }
      }, (error) => {
        console.log("getAllActiveUserRoles() >> " + error.get);
      });
  }

  resetUserAccountForm() {
    this.imageSrc = 'assets/img/user.png';
    this.userRoles = [];
    this.getAllActiveUserRoles();
    this.getAllActiveUserAccounts();
    this.userAccountForm.reset();
    this.userAccountForm.get('commonStatus')?.patchValue('-1');
    this.userAccountForm.get('idUserRole')?.patchValue('-1');
  }
}
