import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OfferReportsComponent } from './offer-reports.component';

describe('OfferReportsComponent', () => {
  let component: OfferReportsComponent;
  let fixture: ComponentFixture<OfferReportsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OfferReportsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OfferReportsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
