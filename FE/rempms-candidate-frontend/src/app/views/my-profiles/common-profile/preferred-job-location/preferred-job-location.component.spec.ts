import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PreferredJobLocationComponent } from './preferred-job-location.component';

describe('PreferredJobLocationComponent', () => {
  let component: PreferredJobLocationComponent;
  let fixture: ComponentFixture<PreferredJobLocationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PreferredJobLocationComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(PreferredJobLocationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
