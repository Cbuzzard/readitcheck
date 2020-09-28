import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.css']
})
export class CommentsComponent implements OnInit {

  @Input()
  comments = [];

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
    this.comments = this.comments.filter((value, index, arr) => {
      return value.id != res;
    })
  }

}
