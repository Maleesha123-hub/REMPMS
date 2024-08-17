import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-preferred-job-location',
  templateUrl: './preferred-job-location.component.html',
  styleUrl: './preferred-job-location.component.scss'
})
export class PreferredJobLocationComponent implements OnInit {

  constructor(private router: Router) {
  }

  ngOnInit(): void {
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
