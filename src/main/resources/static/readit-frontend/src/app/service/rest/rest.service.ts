import { Injectable } from '@angular/core';
import { SubmissionPost } from 'src/app/dto/submission-post';
import { UrlService } from '../url/url.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class RestService {

  constructor(private url: UrlService, private http: HttpClient) { }

  postSubmission(submissionPost: SubmissionPost) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    }
    return this.http.post(`${this.url.backend}/rest/submission`, JSON.stringify(submissionPost), httpOptions);
  }

  getSubmission(id: number) {
    return this.http.get(`${this.url.backend}/rest/submission/${id}`)
  }

  getSubmissions(page: number) {
    return this.http.get(`${this.url.backend}/rest/submission?page=${page}`)
  }

  getLinkPreview(url: string) {
    return this.http.get(`${this.url.backend}/rest/link?url=${url}`)
  }

}
