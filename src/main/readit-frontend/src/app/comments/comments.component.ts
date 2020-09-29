import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.css']
})
export class CommentsComponent implements OnInit {

  @Input()
  comments = [];

  @Output()
  deleted = new EventEmitter<Comment>()

  constructor() { }

  ngOnInit(): void {
  }

  getDateString(timestamp) {
    return new Date(timestamp).toLocaleString()
  }

  shortenTitle(title) {
    return `${title.slice(0, 30)}...`
  }

  onCommentDeleted(res) {
    this.deleted.emit(res)
  }


}
