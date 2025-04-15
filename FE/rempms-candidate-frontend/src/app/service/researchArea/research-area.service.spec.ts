import { TestBed } from '@angular/core/testing';

import { ResearchAreaService } from './research-area.service';

describe('ResearchAreaService', () => {
  let service: ResearchAreaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ResearchAreaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
