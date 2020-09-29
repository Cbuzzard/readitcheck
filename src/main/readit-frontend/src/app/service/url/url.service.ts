import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UrlService {

  constructor() { }

  //prod
  backend: string = ""
  // backend: string = "http://localhost:8080"
}
