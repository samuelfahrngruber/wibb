import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class WibbService {
  constructor(private http: HttpClient) {

  }

  getFeedback(){
    return this.http.get("/api/requests");
  }

  getReports(){
    return this.http.get("/api/reports");
  }

  getErrors(){
    return this.http.get("/api/errors");
  }
}
