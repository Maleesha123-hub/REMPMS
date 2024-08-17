import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecievedCvsComponent } from './recieved-cvs.component';

describe('RecievedCvsComponent', () => {
  let component: RecievedCvsComponent;
  let fixture: ComponentFixture<RecievedCvsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RecievedCvsComponent]
    });
    fixture = TestBed.createComponent(RecievedCvsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
