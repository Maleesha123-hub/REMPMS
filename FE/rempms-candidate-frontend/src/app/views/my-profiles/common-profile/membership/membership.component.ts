import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-membership',
  templateUrl: './membership.component.html',
  styleUrl: './membership.component.scss'
})
export class MembershipComponent implements OnInit {

  constructor(private router: Router) {
  }

  ngOnInit(): void {
  }

  redirectToNext() {
    // Use the Router service to navigate to the 'higher-education' route
    this.router.navigate(['/common-profile/language-proficiency']);
  }

  redirectToBack() {
    // Use the Router service to navigate to the 'higher-education' route
    this.router.navigate(['/common-profile/school-education']);
  }
}
