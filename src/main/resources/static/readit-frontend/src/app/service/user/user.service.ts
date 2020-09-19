import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, XhrFactory } from '@angular/common/http';
import { UrlService } from '../url/url.service';
import { Observable } from 'rxjs';



@Injectable({
  providedIn: 'root'
})
export class UserService {

  gapiSetup: boolean = false;
  loginStatus: boolean = false;
  authInstance: gapi.auth2.GoogleAuth;

  constructor(private http: HttpClient, private url: UrlService) {
    this.initGoogleAuth();
    this.authenticate().then(res => this.loginStatus = JSON.stringify(res) === 'true')
  }

  async authenticate(): Promise<Object> {
    const httpOptions = { headers: new HttpHeaders({}) };
    return await this.http.get(`${this.url.backend}/rest/authenticate`, httpOptions).toPromise();
  }

  async login(): Promise<boolean> {
    return new Promise<boolean>((resolve, reject) => {
      this.authInstance.signIn().then(user => {
        var xhr = new XMLHttpRequest();
        xhr.open('POST', `${this.url.backend}/login`);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.setRequestHeader('X-ID-TOKEN', user.getAuthResponse().id_token);
        xhr.send();
        xhr.onload = async () => {
          sessionStorage.setItem('token', xhr.getResponseHeader('Authorization'))
          await this.authenticate().then(res => this.loginStatus = JSON.stringify(res) === 'true')
          resolve(this.loginStatus);
        };
      })
    })
  }

  async logout(): Promise<boolean> {
    return new Promise<boolean>((resolve, reject) => {
      gapi.auth2.getAuthInstance().signOut().then(async () => {
        sessionStorage.clear();
        await this.authenticate().then(res => this.loginStatus = JSON.stringify(res) === 'true');
        resolve(this.loginStatus)
      })
    })
  }

  initGoogleAuth() {
    if (!this.gapiSetup) {
      const pload = new Promise((resolve) => {
        gapi.load('auth2', resolve);
        gapi.signin2.render("testid", {})
      });
  
      return pload.then(async () => {
        gapi.auth2
          .init({ client_id: '53814632760-ijc3h4mhjnj8gc1mpmha8t2tl8bg9b2v.apps.googleusercontent.com' })
          .then(auth => {
            this.gapiSetup = true;
            this.authInstance = auth;
          });
        });
      }
    }
    

}
