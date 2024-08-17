import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-language-proficiency',
  templateUrl: './language-proficiency.component.html',
  styleUrl: './language-proficiency.component.scss'
})
export class LanguageProficiencyComponent implements OnInit {

  constructor(private router: Router) {
  }

  ngOnInit(): void {
  }

  redirectToNext() {
    // Use the Router service to navigate to the 'higher-education' route
    this.router.navigate(['/common-profile/research']);
  }

  redirectToBack() {
    // Use the Router service to navigate to the 'higher-education' route
    this.router.navigate(['/common-profile/membership']);
  }
}
