import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfessionalExperiencesComponent } from './professional-experiences.component';

describe('ProfessionalExperiencesComponent', () => {
  let component: ProfessionalExperiencesComponent;
  let fixture: ComponentFixture<ProfessionalExperiencesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProfessionalExperiencesComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ProfessionalExperiencesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
