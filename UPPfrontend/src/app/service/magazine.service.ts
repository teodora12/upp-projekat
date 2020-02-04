import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class MagazineService {

  constructor(private http: HttpClient) { }

  startProcess(): any {
    return this.http.get('api/magazines/startProcess');
  }

  getMagazines(processInstanceId): any {
    return this.http.get('api/magazines/getMagazines/'.concat(processInstanceId));
  }

}
