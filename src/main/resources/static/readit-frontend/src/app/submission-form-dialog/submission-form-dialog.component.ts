import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { RestService } from "../service/rest/rest.service"
import { SubmissionPost } from '../dto/submission-post'
import { Question } from '../dto/question'


@Component({
  selector: 'app-submission-form-dialog',
  templateUrl: './submission-form-dialog.component.html',
  styleUrls: ['./submission-form-dialog.component.css']
})
export class SubmissionFormDialogComponent implements OnInit {

  submissionForm;

  constructor(private formBuilder: FormBuilder, private rest: RestService) {
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
    this.rest.postSubmission(new SubmissionPost(form.title, form.link, [new Question(form.question, form.answer)]))
      .subscribe(res => {
        console.log(JSON.stringify(res))

    })
  }
  

}
