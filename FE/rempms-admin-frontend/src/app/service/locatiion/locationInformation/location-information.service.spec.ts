import { TestBed } from '@angular/core/testing';

import { LocationInformationService } from './location-information.service';

describe('LocationInformationService', () => {
  let service: LocationInformationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LocationInformationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
