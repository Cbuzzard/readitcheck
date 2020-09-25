import { EventEmitter, Input, Output, ViewEncapsulation } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Submission } from '../dto/submission';
import { RestService } from '../service/rest/rest.service';
import { Comment } from '../dto/comment'

@Component({
  selector: 'app-new-comment',
  templateUrl: './new-comment.component.html',
  styleUrls: ['./new-comment.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class NewCommentComponent implements OnInit {

  @Input()
  submission: Submission;

  commentContent;
  commentForm: FormGroup;
  commentError = "";

  constructor(private rest: RestService, private formBuilder: FormBuilder) {
    this.commentForm = this.formBuilder.group({
      content: ['', [Validators.required, Validators.maxLength(500)]]
    })
  }

  ngOnInit(): void {
  }

  onCommentSubmit(form) {
    this.rest.postComment(this.submission.id, form.content).subscribe((res: Comment) => {
      if (res) {
        this.submission.comments.unshift(res);
        this.commentContent = ''
      };
    });
  } 

}
