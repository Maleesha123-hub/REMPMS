import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HigherEducationComponent } from './higher-education.component';

describe('HigherEducationComponent', () => {
  let component: HigherEducationComponent;
  let fixture: ComponentFixture<HigherEducationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HigherEducationComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(HigherEducationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
