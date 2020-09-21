import { Component, OnInit, ViewChild } from '@angular/core';
import {MatAccordion} from '@angular/material/expansion';
import { FormBuilder } from '@angular/forms';
import {SubmissionPost} from '../dto/submission-post'
import {Question} from '../dto/question'
import { UserService } from '../service/user/user.service';
import { RestService } from "../service/rest/rest.service"
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { SubmissionFormDialogComponent } from '../submission-form-dialog/submission-form-dialog.component';


@Component({
  selector: 'app-new-submission',
  templateUrl: './new-submission.component.html',
  styleUrls: ['./new-submission.component.css']
})
export class NewSubmissionComponent implements OnInit {

  loginStatus: boolean;

  constructor(private user: UserService, private rest: RestService, public dialog: MatDialog) {
    this.user.loginStatus.subscribe(res => this.loginStatus = res)
  }

  ngOnInit(): void {
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
