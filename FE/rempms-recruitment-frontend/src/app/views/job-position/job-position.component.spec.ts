import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JobPositionComponent } from './job-position.component';

describe('JobPositionComponent', () => {
  let component: JobPositionComponent;
  let fixture: ComponentFixture<JobPositionComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [JobPositionComponent]
    });
    fixture = TestBed.createComponent(JobPositionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
