import { Component, OnInit, Input } from '@angular/core';
import { SubmissionForList } from '../dto/submission-for-list'
import { RestService } from '../service/rest/rest.service';

@Component({
  selector: 'app-simple-submission',
  templateUrl: './simple-submission.component.html',
  styleUrls: ['./simple-submission.component.css']
})
export class SimpleSubmissionComponent implements OnInit {

  @Input()
  submission
  linkPreview: string

  constructor(private rest: RestService) {
  }

  ngOnInit(): void {
    this.rest.getLinkPreview(this.submission.link).subscribe((res: any) => {
      this.linkPreview = res.image
      console.log(res)
    })
  }

}
