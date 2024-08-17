import {Component, OnInit} from '@angular/core';
import {UserRoleService} from "../../service/userRole/user-role.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserRole} from "../../model/userRole/UserRole";
import Swal from "sweetalert2";

@Component({
  selector: 'app-user-role',
  templateUrl: './user-role.component.html',
  styleUrls: ['./user-role.component.scss']
})
export class UserRoleComponent implements OnInit {

  userRoleForm: FormGroup | any;
  userRole: UserRole | any;
  userRoles: UserRole[] | any;

  constructor(private formBuilder: FormBuilder,
              private userRoleService: UserRoleService) {
  }

  ngOnInit(): void {
    this.getAllActiveUserRoles();
    this.userRoleForm = this.formBuilder.group({
      idUserRole: [''],
      userRoleName: ['', Validators.required],
      userRoleDescription: ['', Validators.required],
      commonStatus: ['-1', Validators.required]
    });
  }

  saveUpdateUserRole() {
    if (this.userRoleForm.valid) {
      const userRoleFormValues = this.userRoleForm.value;
      this.userRoleService.saveUpdateUserRole(userRoleFormValues).subscribe(
        (data) => {
          if (data.status == "OK") {
            this.resetUserRoleForm();
            Swal.fire("Saved!", data.message, "success");
          } else {
            this.resetUserRoleForm();
            Swal.fire("Cancelled", data.message, "error");
          }
        },
        (error) => {
          console.log("saveUpdateUserRole() >> " + error.get);
        });

    } else {
      this.userRoleForm.markAllAsTouched();
    }
  }

  getActiveUserRoleById(idUserRole: string) {
    this.userRoleService.getActiveUserRoleById(idUserRole).subscribe(
      (data) => {
        if (data.data != null) {
          this.userRole = data.data;
          //Patch values to the form
          this.userRoleForm.patchValue({
            idUserRole: this.userRole.idUserRole,
            userRoleName: this.userRole.userRoleName,
            userRoleDescription: this.userRole.userRoleDescription,
            commonStatus: this.userRole.commonStatus
          });
        }
      },
      (error) => {
        console.log("getActiveUserRoleById() >> " + error.get);
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

  deleteUserRoleById(idUserRole: string) {
    this.userRoleService.deleteUserRoleById(idUserRole).subscribe(
      (data) => {
        if (data.status == "OK") {
          this.resetUserRoleForm();
          Swal.fire("Deleted!", data.message, "success");
        } else {
          this.resetUserRoleForm();
          Swal.fire("Cancelled", data.message, "error");
        }
      }, (error) => {
        console.log("deleteUserRoleById() >> " + error.get);
      });
  }

  resetUserRoleForm() {
    this.getAllActiveUserRoles();
    this.userRoleForm.reset();
    this.userRoleForm.get('commonStatus')?.patchValue('-1');
  }
}
