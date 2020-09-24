import { EventEmitter, ViewEncapsulation } from '@angular/core';
import { Component, Input, OnInit, Output } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { QuestionCheck } from '../dto/question-check';
import { RestService } from '../service/rest/rest.service';
import { UserService } from '../service/user/user.service';

@Component({
  selector: 'app-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class QuestionComponent implements OnInit {

  @Input()
  question: QuestionCheck;

  @Output()
  questionAnswered = new EventEmitter<boolean>();

  questionForm: FormGroup;
  questionError;

  loginStatus

  constructor(private formBuilder: FormBuilder, private _snackBar: MatSnackBar, private rest: RestService, private user: UserService) {
    this.questionForm = this.formBuilder.group({
      answer: ['', [Validators.required, this.checkForError()]]
    })
  }

  ngOnInit(): void {
    this.user.loginStatus.subscribe(res => {
      this.loginStatus = res;
    })
    this.user.refreshStatus();
  }

  onQuestionSubmit(form) {
    if (this.questionForm.hasError('required')) {
      this.questionError = 'Must enter a value'
    } else {
      this.rest.checkAnswer(this.question.id, form.answer).subscribe((res: boolean) => {
        if (res) {
          this.questionAnswered.emit(res);
          this.openSnackBar("Success! You are now approved to comment.");
        }
        this.questionError = "Incorrect answer"
        this.questionForm.controls['answer'].updateValueAndValidity()
      })
    }
  }

  checkForError(): ValidatorFn {
    return (control: AbstractControl): {[key: string]: any} | null => {
      return this.questionError ? {invalid: this.questionError} : null
    }
  }

  openSnackBar(content: string) {
    this._snackBar.open(content, '', {duration: 5000})
  }

  login() {
    this.user.login().then(es => window.location.reload());
  }

}
