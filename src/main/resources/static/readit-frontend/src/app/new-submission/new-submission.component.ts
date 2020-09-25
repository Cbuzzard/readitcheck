import { Component, OnInit } from '@angular/core';
import { UserService } from '../service/user/user.service';
import { RestService } from "../service/rest/rest.service"
import { MatDialog } from '@angular/material/dialog';
import { SubmissionFormDialogComponent } from '../submission-form-dialog/submission-form-dialog.component';


@Component({
  selector: 'app-new-submission',
  templateUrl: './new-submission.component.html',
  styleUrls: ['./new-submission.component.css']
})
export class NewSubmissionComponent implements OnInit{

  loginStatus: boolean;

  constructor(private user: UserService, private rest: RestService, public dialog: MatDialog) {
    
  }

  ngOnInit(): void {
    this.user.loginStatus.subscribe(res => {
      this.loginStatus = res;
    })
    this.user.refreshStatus();
  }

  openDialog() {
    if (this.loginStatus) {
      const dialogRef = this.dialog.open(SubmissionFormDialogComponent, {
        width: '50%',
        minWidth: '350px',
      })
    } else {
      this.user.login();
    }
  }
}
