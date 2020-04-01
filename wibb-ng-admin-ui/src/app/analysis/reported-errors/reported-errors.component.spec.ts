import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportedErrorsComponent } from './reported-errors.component';

describe('ReportedErrorsComponent', () => {
  let component: ReportedErrorsComponent;
  let fixture: ComponentFixture<ReportedErrorsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportedErrorsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportedErrorsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
