import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SubmissionFormDialogComponent } from './submission-form-dialog.component';

describe('SubmissionFormDialogComponent', () => {
  let component: SubmissionFormDialogComponent;
  let fixture: ComponentFixture<SubmissionFormDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SubmissionFormDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubmissionFormDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
