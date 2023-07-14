import { TestBed } from '@angular/core/testing';

import { RawdataService } from './rawdata.service';

describe('RawdataService', () => {
  let service: RawdataService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RawdataService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
