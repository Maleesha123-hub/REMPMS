import { TestBed } from '@angular/core/testing';

import { JobCategoryService } from './job-category.service';

describe('JobCategoryService', () => {
  let service: JobCategoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JobCategoryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
