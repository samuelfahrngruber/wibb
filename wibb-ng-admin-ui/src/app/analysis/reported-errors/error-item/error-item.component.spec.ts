import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ErrorItemComponent } from './error-item.component';

describe('ErrorItemComponent', () => {
  let component: ErrorItemComponent;
  let fixture: ComponentFixture<ErrorItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ErrorItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ErrorItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
