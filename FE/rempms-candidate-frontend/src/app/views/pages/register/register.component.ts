import { Component, OnInit } from '@angular/core';
import { IconDirective } from '@coreui/icons-angular';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import {
  ContainerComponent,
  RowComponent,
  ColComponent,
  TextColorDirective,
  CardComponent,
  CardBodyComponent,
  FormDirective,
  InputGroupComponent,
  InputGroupTextDirective,
  FormControlDirective,
  ButtonDirective,
} from '@coreui/angular';
import { AuthService } from '../../../service/login/auth/auth.service';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';
import { UserRegisterDTO } from '../../../model/user/register/UserRegisterDTO';
import { CommonModule } from '@angular/common';
import { UserService } from '../../../service/login/user/user.service';
import { UserHasApplicationScopeHasUserRoleRequestDTO } from '../../../model/user/userHasApplicationScopeHasUserRole/UserHasApplicationScopeHasUserRoleRequestDTO';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
  standalone: true,
  imports: [
    ReactiveFormsModule,
    ContainerComponent,
    RowComponent,
    ColComponent,
    CardComponent,
    CardBodyComponent,
    FormDirective,
    InputGroupComponent,
    InputGroupTextDirective,
    IconDirective,
    FormControlDirective,
    ButtonDirective,
    CommonModule,
  ],
})
export class RegisterComponent implements OnInit {
  loading = false;
  registerForm: FormGroup | any;

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private userService: UserService,
  ) {}

  ngOnInit(): void {
    this.registerFormInit();
  }

  registerFormInit() {
    this.registerForm = this.formBuilder.group({
      username: ['', [Validators.required]],
      email: ['', [Validators.required]],
      password: ['', [Validators.required]],
      retypePassword: ['', [Validators.required]],
    });
  }

  register() {
    if (this.registerForm.valid) {
      if (
        this.registerForm.value.password !==
        this.registerForm.value.retypePassword
      ) {
        Swal.fire({
          title: 'Error!',
          text: 'Passwords do not match.',
          icon: 'error',
          confirmButtonText: 'OK',
        });
        return;
      }

      const userHasAuthorizeParties: string[] = [
        'rempms-client',
        'user-auth-client',
      ];

      const userHasApplicationScopeHasUserRoles: UserHasApplicationScopeHasUserRoleRequestDTO[] =
        [
          {
            userHasApplicationScopeHasUserRoleId: '',
            applicationScopeId: '',
            applicationScope: 'rempms_candidate',
            userRole: 'CANDIDATE',
            userRoleId: '',
            hasElements: [],
          },
          {
            userHasApplicationScopeHasUserRoleId: '',
            applicationScopeId: '',
            applicationScope: 'pdev-user',
            userRole: 'NON_AD',
            userRoleId: '',
            hasElements: [],
          },
        ];

      const userRegister: UserRegisterDTO = {
        userId: '',
        userName: this.registerForm.value.username,
        email: this.registerForm.value.email,
        password: this.registerForm.value.password,
        firstName: '',
        lastName: '',
        uuid: '',
        active: true,
        userHasAuthorizeParties: userHasAuthorizeParties,
        userHasApplicationScopeHasUserRoles:
          userHasApplicationScopeHasUserRoles,
      };

      this.userService.register(userRegister).subscribe(
        (response) => {
          this.loading = false;
          if (response.status === 'OK') {
            Swal.fire({
              title: 'Success!',
              text: response.message,
              icon: 'success',
              confirmButtonText: 'OK',
            }).then((result) => {
              if (result.isConfirmed) {
                this.registerForm.reset();
                this.router.navigate(['/login']);
              }
            });
          } else {
            Swal.fire({
              title: 'Error!',
              text: response.message,
              icon: 'error',
              confirmButtonText: 'OK',
            }).then(() => {});
          }
        },
        (error) => {
          Swal.fire({
            title: 'Error!',
            text: error.error.message,
            icon: 'error',
            confirmButtonText: 'OK',
          });
        },
      );
    } else {
      this.registerForm.markAllAsTouched();
    }
  }

  pageLoader() {
    this.loading = true;
  }
}
