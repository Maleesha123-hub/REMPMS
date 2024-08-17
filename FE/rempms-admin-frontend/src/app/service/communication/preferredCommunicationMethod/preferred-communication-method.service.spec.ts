import { TestBed } from '@angular/core/testing';

import { PreferredCommunicationMethodService } from './preferred-communication-method.service';

describe('PreferredCommunicationMethodService', () => {
  let service: PreferredCommunicationMethodService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PreferredCommunicationMethodService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
