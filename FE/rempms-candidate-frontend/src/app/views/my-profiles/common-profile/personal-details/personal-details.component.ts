import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-personal-details',
  templateUrl: './personal-details.component.html',
  styleUrl: './personal-details.component.scss'
})
export class PersonalDetailsComponent implements OnInit {

  constructor(private router: Router) {
  }

  ngOnInit(): void {
  }

  redirectToProfessionalExperience() {
    // Use the Router service to navigate to the 'higher-education' route
    this.router.navigate(['/common-profile/professional-experiences']);
  }
}
