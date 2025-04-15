import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FlaggedJobsComponent } from './flagged-jobs.component';

describe('FlaggedJobsComponent', () => {
  let component: FlaggedJobsComponent;
  let fixture: ComponentFixture<FlaggedJobsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FlaggedJobsComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(FlaggedJobsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
