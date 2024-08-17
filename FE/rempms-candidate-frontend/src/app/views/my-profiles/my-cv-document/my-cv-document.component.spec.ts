import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyCvDocumentComponent } from './my-cv-document.component';

describe('MyCvDocumentComponent', () => {
  let component: MyCvDocumentComponent;
  let fixture: ComponentFixture<MyCvDocumentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MyCvDocumentComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MyCvDocumentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
