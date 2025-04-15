import { TestBed } from '@angular/core/testing';

import { CommonProfileDraftService } from './common-profile-draft.service';

describe('CommonProfileDraftService', () => {
  let service: CommonProfileDraftService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CommonProfileDraftService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
