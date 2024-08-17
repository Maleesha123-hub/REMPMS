import {Component, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import {Router} from "@angular/router";

@Component({
  selector: 'app-school-education',
  templateUrl: './school-education.component.html',
  styleUrl: './school-education.component.scss'
})
export class SchoolEducationComponent  implements OnInit {

  constructor(private router: Router) {
  }

  ngOnInit(): void {
  }

  redirectToNext() {
    // Use the Router service to navigate to the 'higher-education' route
    this.router.navigate(['/common-profile/membership']);
  }

  redirectToBack() {
    // Use the Router service to navigate to the 'higher-education' route
    this.router.navigate(['/common-profile/higher-education']);
  }
}
