import { TestBed } from '@angular/core/testing';

import { RecievedCvsService } from './recieved-cvs.service';

describe('RecievedCvsService', () => {
  let service: RecievedCvsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RecievedCvsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
