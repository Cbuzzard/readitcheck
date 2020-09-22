import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SimpleSubmissionComponent } from './simple-submission.component';

describe('SimpleSubmissionComponent', () => {
  let component: SimpleSubmissionComponent;
  let fixture: ComponentFixture<SimpleSubmissionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SimpleSubmissionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SimpleSubmissionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
