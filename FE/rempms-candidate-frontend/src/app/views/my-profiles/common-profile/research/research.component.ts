import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-research',
  templateUrl: './research.component.html',
  styleUrl: './research.component.scss'
})
export class ResearchComponent implements OnInit {

  constructor(private router: Router) {
  }

  ngOnInit(): void {
  }

  redirectToNext() {
    // Use the Router service to navigate to the 'achievements' route
    this.router.navigate(['/common-profile/achievements']);
  }

  redirectToBack() {
    // Use the Router service to navigate to the 'language-proficiency' route
    this.router.navigate(['/common-profile/language-proficiency']);
  }
}
