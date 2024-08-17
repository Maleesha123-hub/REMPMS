import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JobAlertComponent } from './job-alert.component';

describe('CommonProfileComponent', () => {
  let component: JobAlertComponent;
  let fixture: ComponentFixture<JobAlertComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [JobAlertComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(JobAlertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
