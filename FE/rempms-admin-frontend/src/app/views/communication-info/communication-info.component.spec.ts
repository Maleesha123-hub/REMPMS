import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommunicationInfoComponent } from './communication-info.component';

describe('CommuniactionInfoComponent', () => {
  let component: CommunicationInfoComponent;
  let fixture: ComponentFixture<CommunicationInfoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CommunicationInfoComponent]
    });
    fixture = TestBed.createComponent(CommunicationInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
