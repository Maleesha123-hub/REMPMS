import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-family-information',
  templateUrl: './family-information.component.html',
  styleUrl: './family-information.component.scss'
})
export class FamilyInformationComponent  implements OnInit {

  constructor(private router: Router) {
  }

  ngOnInit(): void {
  }

  redirectToNext() {
    // Use the Router service to navigate to the 'job-preferences' route
    this.router.navigate(['/common-profile/job-preferences']);
  }

  redirectToBack() {
    // Use the Router service to navigate to the 'referees' route
    this.router.navigate(['/common-profile/referees']);
  }

}
