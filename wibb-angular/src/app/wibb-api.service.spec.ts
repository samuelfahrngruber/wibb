import { TestBed } from '@angular/core/testing';

import { WibbApiService } from './wibb-api.service';

describe('WibbApiService', () => {
  let service: WibbApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WibbApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
