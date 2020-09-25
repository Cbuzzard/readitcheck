import { Injectable } from '@angular/core';
import { SubmissionPost } from 'src/app/dto/submission-post';
import { UrlService } from '../url/url.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class RestService {

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  constructor(private url: UrlService, private http: HttpClient) { }

  postSubmission(submissionPost) {
    console.log(submissionPost.title)
    return this.http.post(`${this.url.backend}/rest/submission`, JSON.stringify(submissionPost), this.httpOptions);
  }

  getSubmission(id: number) {
    return this.http.get(`${this.url.backend}/rest/submission/${id}`);
  }

  getSubmissions(page: number) {
    return this.http.get(`${this.url.backend}/rest/submission?page=${page}`);
  }

  getLinkPreview(url: string) {
    return this.http.get(`${this.url.backend}/rest/link?url=${url}`);
  }

  checkAnswer(id: number, answer: string) {
    return this.http.post(`${this.url.backend}/rest/question/${id}`, answer, this.httpOptions);
  }

  postComment(submissionId, content) {
    return this.http.post(`${this.url.backend}/rest/submission/${submissionId}/comment`, content, this.httpOptions);
  }

  getUser(id: number) {
    return this.http.get(`${this.url.backend}/rest/user/${id}`);
  }

  deleteComment(submissionId, commentId) {
    return this.http.delete(`${this.url.backend}/rest/submission/${submissionId}/comment/${commentId}`);
  }

  deleteSubmission(submissionId) {
    return this.http.delete(`${this.url.backend}/rest/submission/${submissionId}`);
  }

}
