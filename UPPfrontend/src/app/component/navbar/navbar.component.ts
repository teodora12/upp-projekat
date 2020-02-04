import { Component, OnInit } from '@angular/core';
import {UserService} from "../../service/user.service";
import {Router} from "@angular/router";
import {MagazineService} from "../../service/magazine.service";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  userRole: any;
  user: any;

  constructor( private router: Router, private userService: UserService, private magazineService: MagazineService) { }

  ngOnInit() {
 //   this.userRole = this.userService.getLoggedUserType();

    const user = JSON.parse(localStorage.getItem('loggedUser'));
    if (user === null) {
      this.userRole = '';
    } else {
      for (const role of user.roles) {
        if (role.authority === 'ROLE_ADMIN') {
          this.userRole = 'ROLE_ADMIN';
        }
      }
    }

    console.log(this.userRole);
    const userTemp = JSON.parse(localStorage.getItem('loggedUser'));
    if (userTemp !== null) {
      this.userService.getUserByUsername(userTemp.sub).subscribe(user => {
        this.user = user;
      });
    }
  }


  logout() {
    localStorage.clear();
    this.userRole = '';
    location.reload();
    this.router.navigate(['/login']);
  }

}
