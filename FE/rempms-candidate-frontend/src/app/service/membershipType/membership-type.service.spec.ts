import { TestBed } from '@angular/core/testing';

import { MembershipTypeService } from './membership-type.service';

describe('MembershipTypeService', () => {
  let service: MembershipTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MembershipTypeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
