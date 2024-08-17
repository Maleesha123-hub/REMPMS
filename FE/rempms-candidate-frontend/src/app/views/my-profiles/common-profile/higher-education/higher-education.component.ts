import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-higher-education',
  templateUrl: './higher-education.component.html',
  styleUrl: './higher-education.component.scss'
})
export class HigherEducationComponent implements OnInit {

  constructor(private router: Router) {
  }

  ngOnInit(): void {
  }

  redirectToNext() {
    // Use the Router service to navigate to the 'higher-education' route
    this.router.navigate(['/common-profile/school-education']);
  }

  redirectToBack() {
    // Use the Router service to navigate to the 'higher-education' route
    this.router.navigate(['/common-profile/professional-experiences']);
  }
}
