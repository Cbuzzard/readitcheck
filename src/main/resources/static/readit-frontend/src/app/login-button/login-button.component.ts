import { Component, OnInit } from '@angular/core';
import { UserService } from '../service/user/user.service'

@Component({
  selector: 'app-login-button',
  templateUrl: './login-button.component.html',
  styleUrls: ['./login-button.component.css']
})
export class LoginButtonComponent implements OnInit {

  loginStatus = this.user.loginStatus;

  constructor(private user: UserService) {
    this.user.authenticate().then(res => this.loginStatus = JSON.stringify(res) === 'true')
  }

  ngOnInit(): void {
    this.setLoginStatus();
  }

  login() {
    this.user.login().then(res => this.loginStatus = res);
  }

  logout() {
    this.user.logout().then(res => this.loginStatus = res);
  }

  setLoginStatus() {
    this.loginStatus = this.user.loginStatus;
  }

  changeStatus() {
    this.loginStatus = !this.loginStatus;
  }

}
