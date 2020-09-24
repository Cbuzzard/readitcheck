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

}
