import { TestBed } from '@angular/core/testing';

import { HigherEduQualificationService } from './higher-edu-qualification.service';

describe('HigherEduQualificationService', () => {
  let service: HigherEduQualificationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HigherEduQualificationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
