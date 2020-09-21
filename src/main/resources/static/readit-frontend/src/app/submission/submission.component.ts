import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { RestService } from '../service/rest/rest.service';

@Component({
  selector: 'app-submission',
  templateUrl: './submission.component.html',
  styleUrls: ['./submission.component.css']
})
export class SubmissionComponent implements OnInit {

  submission = null;

  constructor(private route: ActivatedRoute, private rest: RestService) {
    this.route.params.subscribe(param => {
      this.rest.getSubmission(param.id).subscribe(res => this.submission = res);
    })
  }

  ngOnInit(): void {
  }

}
