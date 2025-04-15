import { Component, OnInit } from '@angular/core';
import { NgStyle } from '@angular/common';
import { IconDirective } from '@coreui/icons-angular';
import { FormBuilder, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../service/login/auth/auth.service';
import { UserService } from '../../../service/login/user/user.service';
import { ReactiveFormsModule } from '@angular/forms';
import {
  ContainerComponent,
  RowComponent,
  ColComponent,
  CardGroupComponent,
  CardComponent,
  CardBodyComponent,
  FormDirective,
  InputGroupComponent,
  InputGroupTextDirective,
  FormControlDirective,
  ButtonDirective,
} from '@coreui/angular';
import { Router } from '@angular/router';
import { FormGroup } from '@angular/forms';
import Swal from 'sweetalert2';
import { Subject, takeUntil } from 'rxjs';
import { AuthStatus } from '../../../enums/AuthStatus';
import { UserRoles } from '../../../enums/UserRole';
import { UserDetailsResponseDTO } from '../../../model/user/user-details/UserDetailsResponseDTO';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  standalone: true,
  imports: [
    ReactiveFormsModule,
    CommonModule,
    ContainerComponent,
    RowComponent,
    ColComponent,
    CardGroupComponent,
    CardComponent,
    CardBodyComponent,
    FormDirective,
    InputGroupComponent,
    InputGroupTextDirective,
    IconDirective,
    FormControlDirective,
    ButtonDirective,
    NgStyle,
  ],
})
export class LoginComponent implements OnInit {
  loading = false;
  loginForm: FormGroup | any;

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private userService: UserService,
  ) {}

  private unsubscribe$ = new Subject<void>();

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]],
    });
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  login() {
    if (this.loginForm.valid) {
      this.pageLoader();
      const username: string = this.loginForm.value.username;
      const password: string = this.loginForm.value.password;
      this.authService.login(username, password).subscribe(
        (response) => {
          if (response.status === 'ACCEPTED') {
            /**
             *
             * << Session Storage >>
             * Data persists only for the duration of the page session. Once the browser tab is closed, the data is deleted.
             * Scoped to the specific tab where it was created. Other tabs or windows cannot access it.
             * Best for storing temporary data, such as form inputs or UI state for a single session.
             * Typically, 5–10 MB depending on the browser.
             * Key-value pairs stored as strings.
             *
             * << Local Storage >>
             * Data persists indefinitely, even after the browser is closed and reopened.
             * Shared across all tabs and windows of the same origin (domain, protocol, and port).
             * Best for storing data that needs to persist between sessions, like user preferences or settings.
             * Typically, 5–10 MB depending on the browser.
             * Key-value pairs stored as strings.
             **/
            sessionStorage.setItem('accessToken', response.data.accessToken);
            sessionStorage.setItem('refreshToken', response.data.refreshToken);

            // Get user permission list by token and uuid for the specific application scope
            this.getUserPermissionList();
          } else {
            Swal.fire({
              title: 'Error!',
              text: response.message,
              icon: 'error',
              confirmButtonText: 'OK',
            });
          }
        },
        (error) => {
          this.loading = false;
          Swal.fire({
            title: 'Error!',
            text: error.error.message,
            icon: 'error',
            confirmButtonText: 'OK',
          });
        },
      );
    } else {
      this.loginForm.markAllAsTouched();
    }
  }

  getUserPermissionList() {
    this.userService.getUserPermissionList().subscribe(
      (response) => {
        if (response.status === 'OK') {
          sessionStorage.setItem('userDetails', JSON.stringify(response.data));
          const userDetails: UserDetailsResponseDTO = response.data;

          this.loading = false;

          if (
            userDetails.userHasApplicationScopeHasUserRole.userRole.role ===
              UserRoles.CANDIDATE ||
            userDetails.userHasApplicationScopeHasUserRole.userRole.role ===
              UserRoles.ADMIN
          ) {
            this.authService.setAuthenticationStatus(AuthStatus.YES);
            this.router.navigate(['/job-vacancies']);
          } else {
            this.authService.setAuthenticationStatus(AuthStatus.NO);
            Swal.fire({
              title: 'Error!',
              text: 'Unauthorized Access!',
              icon: 'error',
              confirmButtonText: 'OK',
            });
          }
        } else {
          Swal.fire({
            title: 'Error!',
            text: response.message,
            icon: 'error',
            confirmButtonText: 'OK',
          });
        }
      },
      (error) => {
        this.loading = false;
        Swal.fire({
          title: 'Error!',
          text: 'Network Error.',
          icon: 'error',
          confirmButtonText: 'OK',
        });
      },
    );
  }

  pageLoader() {
    this.loading = true;
  }

  redirectToRegister() {
    // Use the Router service to navigate to the 'referees' route
    this.router.navigate(['/register']);
  }
}
