import { Injectable } from '@angular/core';


import { HttpClient, HttpHeaders } from '@angular/common/http';
// import { Http, Response, Headers, RequestOptions, ResponseContentType } from '@angular/http';

import { Observable } from 'rxjs';
import {map} from 'rxjs/operators';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient, private router: Router) {  }

  getFormFields(): any {
    return this.http.get('api/users/startProcess');
  }

  register(user, o, taskId): any {
    return this.http.post('api/users/register', user);
  }


    login(user): any {
        return this.http.post('api/auth/login', user, {observe: 'response'}).pipe(map(response => response));
    }

    getToken(): string {
        const currentUser = JSON.parse(localStorage.getItem('loggedUser'));
        if (currentUser !== null) {
            currentUser.token = currentUser.token.replace(':', '');
        }
        const token = currentUser && currentUser.token;
        return token ? token : '';
    }

    getLoggedUserType() {
        const user = JSON.parse(localStorage.getItem('loggedUser'));
        let userRole;
        if (user === null) {
            userRole = '';
        } else {
            for (const role of user.roles) {
                if (role === 'ADMIN') {
                    userRole = 'ADMIN';
                }
            }
        }
        return userRole;
    }

    isLoggedIn() {
        const user = JSON.parse(localStorage.getItem('loggedUser'));

        if (user === null) {
            return false;
        }
        const expiredDate = new Date(new Date(parseInt(user.exp, 10) * 1000));
        const nowDate = new Date();
        if ((expiredDate.getDate() <= nowDate.getDate()) &&
            (expiredDate.getTime() <= nowDate.getTime())) {
            this.logout();
            return false;
        }
        return true;

    }



    getUserByUsername(username): any {
        return this.http.get('api/users/'.concat(username));
    }

    logout() {
        localStorage.clear();
        this.router.navigate(['/login']);
    }

}
