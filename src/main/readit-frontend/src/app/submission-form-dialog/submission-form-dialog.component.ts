import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RestService } from "../service/rest/rest.service"
import { SubmissionPost } from '../dto/submission-post'
import { QuestionPost } from '../dto/question-post'
import { Router } from '@angular/router';
import { MatDialogRef } from '@angular/material/dialog';


@Component({
  selector: 'app-submission-form-dialog',
  templateUrl: './submission-form-dialog.component.html',
  styleUrls: ['./submission-form-dialog.component.css']
})
export class SubmissionFormDialogComponent implements OnInit {

  linkReg = /https?:\/\/(www\.)[-a-zA-Z0-9@:%._\+~#=]{2,256}\.[a-z]{2,4}\b([-a-zA-Z0-9@:%_\+.~#?&//=]*)/gi
  submissionForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private rest: RestService, private router: Router, public dialogRef: MatDialogRef<SubmissionFormDialogComponent>) {
    this.submissionForm = this.formBuilder.group({
      title: ['', [Validators.required, Validators.maxLength(255)]],
      link: ['', [Validators.required, Validators.maxLength(1000), Validators.pattern(this.linkReg)]],
      question: ['', [Validators.required, Validators.maxLength(255)]],
      answer: ['', [Validators.required, Validators.maxLength(25)]]
    })
  }

  ngOnInit(): void {
  }

  onSubmit(form) {
    this.rest.postSubmission(new SubmissionPost(form.title, form.link, new QuestionPost(form.question, form.answer))).subscribe(res => {
        this.router.navigateByUrl(`/submission/${res}`)
        this.dialogRef.close()
      })
  }

  getErrorMessage(field) {
    switch(field) {
      case 'title':
        if (this.submissionForm.get(field).hasError('required')) return "required"
        if (this.submissionForm.get(field).hasError('maxlength')) return "must be less than 255 characters"
        break;
      case 'link':
        if (this.submissionForm.get(field).hasError('required')) return "required"
        if (this.submissionForm.get(field).hasError('maxlength')) return "must be less than 1000 characters"
        if (this.submissionForm.get(field).hasError('pattern')) return "must be in format of https://www.ex.com"
        break;
      case 'question':
        if (this.submissionForm.get(field).hasError('required')) return "required"
        if (this.submissionForm.get(field).hasError('maxlength')) return "must be less than 255 characters"
        break;
      case 'answer':
        if (this.submissionForm.get(field).hasError('required')) return "required"
        if (this.submissionForm.get(field).hasError('maxlength')) return "must be less than 25 characters"
        break;
    }
    
  }

}

