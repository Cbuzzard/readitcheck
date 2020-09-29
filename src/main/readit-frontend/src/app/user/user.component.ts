import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { RestService } from '../service/rest/rest.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class UserComponent implements OnInit {

  user;
  selectedTab = 0;

  constructor(private rest: RestService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.getUser();
  }

  getUser() {
    this.route.params.subscribe(param => {
      this.rest.getUser(param.id).subscribe(res => {
        this.user = res
      })
    })
  }

  tabChange(tab) {
    this.selectedTab = tab.index;
  }

  commentDeleted(res) {
    this.user.comments = this.user.comments.filter((value, index, arr) => {
      return value.id != res;
    })
  }

}
