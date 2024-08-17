import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-achievements',
  templateUrl: './achievements.component.html',
  styleUrl: './achievements.component.scss'
})
export class AchievementsComponent implements OnInit {

  constructor(private router: Router) {
  }

  ngOnInit(): void {
  }

  redirectToNext() {
    // Use the Router service to navigate to the 'referees' route
    this.router.navigate(['/common-profile/referees']);
  }

  redirectToBack() {
    // Use the Router service to navigate to the 'research' route
    this.router.navigate(['/common-profile/research']);
  }
}
