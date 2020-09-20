import { Component, OnInit, ViewChild } from '@angular/core';
import {MatAccordion} from '@angular/material/expansion';
import { FormBuilder } from '@angular/forms';
import {SubmissionPost} from '../dto/submission-post'
import {Question} from '../dto/question'


@Component({
  selector: 'app-new-submission',
  templateUrl: './new-submission.component.html',
  styleUrls: ['./new-submission.component.css']
})
export class NewSubmissionComponent implements OnInit {

  submissionForm;

  @ViewChild(MatAccordion) accordion: MatAccordion;

  constructor(private formBuilder: FormBuilder) {
    this.submissionForm = this.formBuilder.group({
      title: '',
      link: '',
      question: '',
      answer: ''
    })
  }

  ngOnInit(): void {
  }

  onSubmit(submissionData) {
    let question = new Question(submissionData.question, submissionData.answer);
    let questions: Array<Question> = []
    questions.push(question)
    let submission = new SubmissionPost(submissionData.title, submissionData.link, questions)
    console.log(submission)
  }

}
