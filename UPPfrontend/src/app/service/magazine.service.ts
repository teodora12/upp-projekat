import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class MagazineService {

  constructor(private http: HttpClient) { }

  startProcess(username): any {
    return this.http.get('api/magazines/startProcess/'.concat(username));
  }

  getMagazines(processInstanceId): any {
    return this.http.get('api/magazines/getMagazines/'.concat(processInstanceId));
  }

  submitForm(magazine, taskId): any {
    return this.http.post('api/magazines/submitChoosenMagazine/'.concat(taskId), magazine);
  }

  getFormFields(processInstanceId): any {
    return this.http.get('api/magazines/getFormFields/'.concat(processInstanceId));
  }

  getScientificFieldsForMagazine(magazineTitle): any {
    return this.http.get('api/magazines/getScientificFieldsForMagazine/'.concat(magazineTitle));
  }

  submitWorkData(workData, taskId): any {
    return this.http.post('api/magazines/submitWorkData/'.concat(taskId), workData);
  }

  getFormFieldsPayment(processInstanceId): any {
    return this.http.get('api/magazines/getFormFieldsPayment/'.concat(processInstanceId));
  }

  submitPayment(taskId, o): any {
    return this.http.post('api/magazines/submitPayment/'.concat(taskId), o);
  }

  isActiveMembership(processInstanceId): any {
      return this.http.get('api/magazines/isActiveDues/'.concat(processInstanceId));
  }

}
