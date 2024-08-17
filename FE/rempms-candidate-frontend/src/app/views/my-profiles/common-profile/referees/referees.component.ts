import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-referees',
  templateUrl: './referees.component.html',
  styleUrl: './referees.component.scss'
})
export class RefereesComponent implements OnInit {

  constructor(private router: Router) {
  }

  ngOnInit(): void {
  }

  redirectToNext() {
    // Use the Router service to navigate to the 'family-information' route
    this.router.navigate(['/common-profile/family-information']);
  }

  redirectToBack() {
    // Use the Router service to navigate to the 'achievements' route
    this.router.navigate(['/common-profile/achievements']);
  }

}
