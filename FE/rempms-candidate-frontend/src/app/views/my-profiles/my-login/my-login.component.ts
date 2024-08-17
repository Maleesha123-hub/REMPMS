import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';

@Component({
  selector: 'app-my-login',
  templateUrl: './my-login.component.html',
  styleUrl: './my-login.component.scss'
})
export class MyLoginComponent implements OnInit{

  imageSrc: any = 'assets/img/user.png';
  @ViewChild('fileInput') fileInputRef!: ElementRef;

  ngOnInit(): void {
  }

  constructor() {
  }

  previewImage() {
    const fileInputElement = this.fileInputRef.nativeElement as HTMLInputElement;
    const userImage = fileInputElement.files?.[0];

  }
}
