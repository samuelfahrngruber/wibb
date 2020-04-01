import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OfferReportItemComponent } from './offer-report-item.component';

describe('OfferReportItemComponent', () => {
  let component: OfferReportItemComponent;
  let fixture: ComponentFixture<OfferReportItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OfferReportItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OfferReportItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
