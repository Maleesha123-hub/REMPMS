import { TestBed } from '@angular/core/testing';

import { AreaOfStudyService } from './area-of-study.service';

describe('AreaOfStudyService', () => {
  let service: AreaOfStudyService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AreaOfStudyService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
