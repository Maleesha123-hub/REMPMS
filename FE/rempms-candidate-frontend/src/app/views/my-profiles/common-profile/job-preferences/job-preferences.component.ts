import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-job-preferences',
  templateUrl: './job-preferences.component.html',
  styleUrl: './job-preferences.component.scss'
})
export class JobPreferencesComponent implements OnInit {

  constructor(private router: Router) {
  }

  ngOnInit(): void {
  }

  redirectToNext() {
    // Use the Router service to navigate to the 'preferred-job-location' route
    this.router.navigate(['/common-profile/preferred-job-location']);
  }

  redirectToBack() {
    // Use the Router service to navigate to the 'family-information' route
    this.router.navigate(['/common-profile/family-information']);
  }

}
