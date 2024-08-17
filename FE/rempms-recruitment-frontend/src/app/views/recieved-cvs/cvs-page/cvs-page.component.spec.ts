import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CvsPageComponent } from './cvs-page.component';

describe('CvsPageComponent', () => {
  let component: CvsPageComponent;
  let fixture: ComponentFixture<CvsPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CvsPageComponent]
    });
    fixture = TestBed.createComponent(CvsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
