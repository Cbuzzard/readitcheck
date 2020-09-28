import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RestService } from '../service/rest/rest.service';
import { Submission } from '../dto/submission'
import { UserService } from '../service/user/user.service';
import { Comment } from '../dto/comment'
import { Subscription } from 'rxjs';


@Component({
  selector: 'app-submission',
  templateUrl: './submission.component.html',
  styleUrls: ['./submission.component.css'],
})
export class SubmissionComponent implements OnInit {

  submission: Submission;
  linkPreview: string;
  loginStatus: boolean;
  userSubscription: Subscription;

  constructor(private route: ActivatedRoute, private router: Router, private rest: RestService, private user: UserService) {}

  ngOnInit(): void {
    this.userSubscription = this.user.loginStatus.subscribe(res => {
      this.loginStatus = res;
    })
    this.user.refreshStatus();
    this.getSubmission()
  }

  getSubmission() {
    this.route.params.subscribe(param => {
      this.rest.getSubmission(param.id).subscribe((res: Submission) => {
        this.submission = res
        this.rest.getLinkPreview(this.submission.link).subscribe((res: any) => {
          this.linkPreview = res ? res.image : ''
        })
      });
    })
  }

  getDateString(timestamp) {
    let d = new Date(timestamp)
    return d.toLocaleString()
  }

  questionAnswered(res) {
    this.submission.currentUserApproved = res;
  }

  onDeleted() {
    this.userSubscription.unsubscribe();
    this.router.navigateByUrl('')
  }

  

}
