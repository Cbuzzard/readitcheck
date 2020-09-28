import { Component, OnInit, Input } from '@angular/core';
import { Submission } from '../dto/submission';
import { RestService } from '../service/rest/rest.service';

@Component({
  selector: 'app-simple-submission',
  templateUrl: './simple-submission.component.html',
  styleUrls: ['./simple-submission.component.css']
})
export class SimpleSubmissionComponent implements OnInit {

  @Input()
  submission: Submission;
  linkPreview: string
  deleted: boolean


  constructor(private rest: RestService) {}

  ngOnInit(): void {
    this.rest.getLinkPreview(this.submission.link).subscribe((res: any) => {
      this.linkPreview = res ? res.image : ''
    })
  }

  getDateString(timestamp) {
    return new Date(timestamp).toLocaleString()
  }

  onDeleted() {
    this.deleted = true;
  }

}
