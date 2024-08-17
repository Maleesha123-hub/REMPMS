import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-cv-or-certificates',
  templateUrl: './cv-or-certificates.component.html',
  styleUrl: './cv-or-certificates.component.scss'
})
export class CvOrCertificatesComponent implements OnInit {

  imageSrc: any = 'assets/img/default_image.jpg';
  @ViewChild('fileInput') fileInputRef!: ElementRef;

  ngOnInit(): void {
  }

  constructor(private router: Router) {
  }

  previewImage() {
    const fileInputElement = this.fileInputRef.nativeElement as HTMLInputElement;
    const userImage = fileInputElement.files?.[0];

  }

  redirectToBack() {
    // Use the Router service to navigate to the 'preferred-job-location' route
    this.router.navigate(['/common-profile/preferred-job-location']);
  }
}
