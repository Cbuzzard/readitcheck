import { Component, OnInit } from '@angular/core';
import { SubmissionForList } from '../dto/submission-for-list';
import { RestService } from '../service/rest/rest.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
})
export class HomeComponent implements OnInit {

  page: number = 0;
  submissions: Array<SubmissionForList> = []

  constructor(private rest: RestService) {
    this.getSubmissions()
  }

  ngOnInit(): void {
  }

  onScroll() {
    this.getSubmissions()
  }

  getSubmissions() {
    this.rest.getSubmissions(this.page).subscribe((res: Array<SubmissionForList>) => {
      this.submissions.push(...res)
    })
    this.page++
  }

}
