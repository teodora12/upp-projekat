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

  getFormFieldsMRedactor(processInstanceId): any {
    return this.http.get('api/magazines/getFormFieldsMRedactor/'.concat(processInstanceId));
  }

  submitPayment(taskId, o): any {
    return this.http.post('api/magazines/submitPayment/'.concat(taskId), o);
  }

  isActiveMembership(processInstanceId): any {
      return this.http.get('api/magazines/isActiveDues/'.concat(processInstanceId));
  }

  submitCoauthorData(o, taskId): any {
    return this.http.post('api/magazines/submitCoauthor/'.concat(taskId), o);
  }

  getWorksForMainRedactor(processInstanceId): any {
    return this.http.get('api/magazines/getWorks/'.concat(processInstanceId));
  }

  submitMainRedactor(o, taskId): any {
    return this.http.post('api/magazines/submitMainRedactorOverview/'.concat(taskId), o);

  }

  submitMainRedactorAcceptPdf(o, taskId): any {
    return this.http.post('api/magazines/submitMainRedactorAcceptPdf/'.concat(taskId), o);

  }

  submitComment(o, taskId): any {
    return this.http.post('api/magazines/submitCommentMainRedactor/'.concat(taskId), o);
  }

  getFormFieldsForCorrectionWork(processInstanceId): any {
      return this.http.get('api/magazines/getWorkForCorrection/'.concat(processInstanceId));
  }

  submitCorrection(o, taskId): any {
    return this.http.post('api/magazines/submitCorrection/'.concat(taskId), o);
  }

}
