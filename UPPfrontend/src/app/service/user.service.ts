import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {  }

  getFormFields(): any {
    return this.http.get('api/users/startProcess');
  }

  register(user, o, taskId): any {
    return this.http.post('api/users/register', user);
  }
}
