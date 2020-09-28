import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { RestService } from '../service/rest/rest.service';
import { UserService } from '../service/user/user.service';

@Component({
  selector: 'app-delete-button',
  templateUrl: './delete-button.component.html',
  styleUrls: ['./delete-button.component.css']
})
export class DeleteButtonComponent implements OnInit {

  @Input()
  content;

  @Output()
  deleted = new EventEmitter<number>()

  isOwner: boolean;

  constructor(private rest: RestService) {
  }

  ngOnInit(): void {
    this.isOwner = this.checkUser();
  }

  checkUser(): boolean {
    return this.content.userId === sessionStorage.getItem('userId');
  }

  onDelete() {
    if(this.content.commentId) {
      this.rest.deleteComment(this.content.submissionId, this.content.commentId).subscribe(res => this.deleted.emit(this.content.commentId))
    } else {
      this.rest.deleteSubmission(this.content.submissionId).subscribe(res => this.deleted.emit(this.content.submissionId))

    }
  }

}
