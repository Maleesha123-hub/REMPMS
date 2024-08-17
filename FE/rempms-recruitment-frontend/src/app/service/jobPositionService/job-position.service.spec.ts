import { TestBed } from '@angular/core/testing';

import { JobPositionService } from './job-position.service';

describe('JobPositionService', () => {
  let service: JobPositionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JobPositionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
