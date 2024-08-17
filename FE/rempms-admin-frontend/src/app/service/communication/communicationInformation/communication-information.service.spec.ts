import { TestBed } from '@angular/core/testing';

import { CommunicationInformationService } from './communication-information.service';

describe('CommunicationInformationService', () => {
  let service: CommunicationInformationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CommunicationInformationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
