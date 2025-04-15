import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CvOrCertificatesComponent } from './cv-or-certificates.component';

describe('CvOrCertificatesComponent', () => {
  let component: CvOrCertificatesComponent;
  let fixture: ComponentFixture<CvOrCertificatesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CvOrCertificatesComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(CvOrCertificatesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
