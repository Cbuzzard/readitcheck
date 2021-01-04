import { Injectable } from '@angular/core';
import { SubmissionPost } from 'src/app/dto/submission-post';
import { UrlService } from '../url/url.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../../environments/environment'

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
    return this.http.post(`${environment.backend}/rest/submission`, JSON.stringify(submissionPost), this.httpOptions);
  }

  getSubmission(id: number) {
    return this.http.get(`${environment.backend}/rest/submission/${id}`);
  }

  getSubmissions(page: number) {
    return this.http.get(`${environment.backend}/rest/submission?page=${page}`);
  }

  checkAnswer(id: number, answer: string) {
    return this.http.post(`${environment.backend}/rest/question/${id}`, answer, this.httpOptions);
  }

  postComment(submissionId, content) {
    return this.http.post(`${environment.backend}/rest/submission/${submissionId}/comment`, {content: content}, this.httpOptions);
  }

  getUser(id: number) {
    return this.http.get(`${environment.backend}/rest/user/${id}`);
  }

  deleteComment(submissionId, commentId) {
    return this.http.delete(`${environment.backend}/rest/submission/${submissionId}/comment/${commentId}`);
  }

  deleteSubmission(submissionId) {
    return this.http.delete(`${environment.backend}/rest/submission/${submissionId}`);
  }

}
