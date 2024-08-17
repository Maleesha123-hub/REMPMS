import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-professional-experiences',
  templateUrl: './professional-experiences.component.html',
  styleUrl: './professional-experiences.component.scss'
})
export class ProfessionalExperiencesComponent implements OnInit {

  constructor(private router: Router) {
  }

  ngOnInit(): void {
  }

  redirectToNext() {
    // Use the Router service to navigate to the 'higher-education' route
    this.router.navigate(['/common-profile/higher-education']);
  }

  redirectToBack() {
    // Use the Router service to navigate to the 'higher-education' route
    this.router.navigate(['/common-profile/personal-details']);
  }
}
