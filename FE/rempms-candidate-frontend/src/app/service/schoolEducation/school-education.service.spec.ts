import { TestBed } from '@angular/core/testing';

import { SchoolEducationService } from './school-education.service';

describe('SchoolEducationService', () => {
  let service: SchoolEducationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SchoolEducationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
