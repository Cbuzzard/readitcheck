import { EventEmitter, Input, ViewChild, ViewEncapsulation, NgZone } from '@angular/core';
import {CdkTextareaAutosize} from '@angular/cdk/text-field';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Submission } from '../dto/submission';
import { RestService } from '../service/rest/rest.service';
import { Comment } from '../dto/comment'
import {take} from 'rxjs/operators';

@Component({
  selector: 'app-new-comment',
  templateUrl: './new-comment.component.html',
  styleUrls: ['./new-comment.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class NewCommentComponent implements OnInit {

  @Input()
  submission: Submission;

  @ViewChild('autosize') autosize: CdkTextareaAutosize;

  commentContent;
  commentForm: FormGroup;

  constructor(private rest: RestService, private formBuilder: FormBuilder, private _ngZone: NgZone) {
    this.commentForm = this.formBuilder.group({
      content: ['', [Validators.required, Validators.maxLength(3000)]]
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

  getErrorMessage() {
    if (this.commentForm.get('content').hasError('required')) return "required"
    if (this.commentForm.get('content').hasError('maxlength')) return "must be less than 3000 characters"
  }

  triggerResize() {

    this._ngZone.onStable.pipe(take(1))
        .subscribe(() => this.autosize.resizeToFitContent(true));
  }

}
