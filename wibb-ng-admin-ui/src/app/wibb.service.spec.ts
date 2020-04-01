import { TestBed } from '@angular/core/testing';

import { WibbService } from './wibb.service';

describe('WibbService', () => {
  let service: WibbService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WibbService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
