import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
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

  submissionForm;

  constructor(private formBuilder: FormBuilder, private rest: RestService, private router: Router, public dialogRef: MatDialogRef<SubmissionFormDialogComponent>) {
    this.submissionForm = this.formBuilder.group({
      title: '',
      link: '',
      question: '',
      answer: ''
    })
  }

  ngOnInit(): void {
  }

  onSubmit(form) {
    this.rest.postSubmission(new SubmissionPost(form.title, form.link, new QuestionPost(form.question, form.answer)))
      .subscribe(res => {
        this.router.navigateByUrl(`/submission/${res}`)
        this.dialogRef.close()
      })
  }

}
