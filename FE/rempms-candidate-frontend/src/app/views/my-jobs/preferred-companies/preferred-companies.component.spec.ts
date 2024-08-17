import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PreferredCompaniesComponent } from './preferred-companies.component';

describe('PreferredCompaniesComponent', () => {
  let component: PreferredCompaniesComponent;
  let fixture: ComponentFixture<PreferredCompaniesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PreferredCompaniesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PreferredCompaniesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
