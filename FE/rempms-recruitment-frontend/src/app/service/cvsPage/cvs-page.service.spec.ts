import { TestBed } from '@angular/core/testing';

import { CvsPageService } from './cvs-page.service';

describe('CvsPageService', () => {
  let service: CvsPageService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CvsPageService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
