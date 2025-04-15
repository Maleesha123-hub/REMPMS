import { TestBed } from '@angular/core/testing';

import { HigherEducationService } from './higher-education.service';

describe('HigherEducationService', () => {
  let service: HigherEducationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HigherEducationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
