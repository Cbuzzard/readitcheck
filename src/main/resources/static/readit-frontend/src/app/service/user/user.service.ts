import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, XhrFactory } from '@angular/common/http';
import { UrlService } from '../url/url.service';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  gapiSetup: boolean = false;
  loginStatus: Subject<boolean> = new Subject<boolean>();
  authInstance: gapi.auth2.GoogleAuth;

  constructor(private http: HttpClient, private url: UrlService) {
    this.initGoogleAuth();
    this.authenticate().then(res => this.loginStatus.next(JSON.stringify(res) === 'true'))
  }

  async authenticate(): Promise<Object> {
    const httpOptions = { headers: new HttpHeaders({}) };
    return await this.http.get(`${this.url.backend}/rest/authenticate`, httpOptions).toPromise();
  }

  login() {
    this.authInstance.signIn().then(user => {
      var xhr = new XMLHttpRequest();
      xhr.open('POST', `${this.url.backend}/login`);
      xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
      xhr.setRequestHeader('X-ID-TOKEN', user.getAuthResponse().id_token);
      xhr.send();
      xhr.onload = async () => {
        sessionStorage.setItem('token', xhr.getResponseHeader('Authorization'))
        this.authenticate().then(res => this.loginStatus.next(JSON.stringify(res) === 'true'))
      };
    })
  }

  logout() {
    gapi.auth2.getAuthInstance().signOut().then(async () => {
      sessionStorage.clear();
      await this.authenticate().then(res => this.loginStatus.next(JSON.stringify(res) === 'true'));
    })
  }

  async initGoogleAuth() {
    if (!this.gapiSetup) {
      const pload = new Promise((resolve) => {
        gapi.load('auth2', resolve);
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
