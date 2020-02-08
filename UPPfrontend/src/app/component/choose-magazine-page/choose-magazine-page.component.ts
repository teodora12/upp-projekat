import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {ToastrManager} from 'ng6-toastr-notifications';
import {MagazineService} from '../../service/magazine.service';

@Component({
  selector: 'app-choose-magazine-page',
  templateUrl: './choose-magazine-page.component.html',
  styleUrls: ['./choose-magazine-page.component.css']
})
export class ChooseMagazinePageComponent implements OnInit {

  private formFieldsDTO = null;
  private formFields = [];
  private processInstance = '';
  private enumValues = [];
  private tasks = [];
  private processInstanceId;
  magazines: any;
  choosenMagazine: any;
  isOpenAccess: boolean;
  userCredentials: any;
  membership: boolean;

  constructor( private router: Router, private toastr: ToastrManager, private magazineService: MagazineService) {
    this.userCredentials = {username: ''};
  }

  ngOnInit() {

    this.userCredentials = JSON.parse(localStorage.getItem('loggedUser'));
    console.log(this.userCredentials);
    console.log(this.userCredentials.sub + 'AAAAAAAAAAAAAAAAAAAAAAAAAAA');

    this.startProcess(this.userCredentials.sub);


  }

  startProcess(username) {
    this.magazineService.startProcess(username).subscribe(formFieldsDTO => {
      this.formFieldsDTO = formFieldsDTO;
      this.formFields = formFieldsDTO.formFields;
      this.processInstance = formFieldsDTO.processInstanceId;
      this.processInstanceId = formFieldsDTO.processInstanceId;
      // this.formFields.forEach( (field) => {
      //
      //       if ( field.type.name === 'enum') {
      //         this.enumValues = Object.keys(field.type.values);
      //       }
      //     },
      //     err => {
      //       console.log('Error occured');
      //     }
      // );

      this.getMagazines(this.processInstanceId);

      this.isActiveMembership(this.processInstanceId);
      console.log(this.membership);

    });

  }

  getMagazines(processInstanceId) {
    this.magazineService.getMagazines(processInstanceId).subscribe(magazines => {
      this.magazines = magazines;

      this.magazines.forEach( (field) => {
        this.enumValues.push(field.title);
  //      console.log(field.title);
//        console.log(field.id);

          //      this.enumValues = Object.keys(field['title']);
        console.log(this.enumValues);

      } );

    });
  }

  choose(value, form) {

    console.log(this.choosenMagazine);

    const o = new Array();
    for (const property in value) {
   //       console.log(property);
     //     console.log(value[property]);
          o.push({fieldId : property, fieldValue : value[property]});
      }

    console.log(o);
    this.submit(o, this.formFieldsDTO.taskId);


      // this.magazines.forEach((field) => {
    //   if (field.title.equals(this.choosenMagazine)) {
    //     this.submit(field, this.formFieldsDTO.taskId);
    //   }
    // });

  }

  submit(magazine, taskId) {
    this.magazineService.submitForm(magazine, taskId).subscribe(res => {
      this.isOpenAccess = res;
      console.log(res);
      this.toastr.successToastr('Success!');
      console.log(magazine[0].fieldValue);

      if (this.isOpenAccess === false) {
        this.router.navigate(['/workData', this.processInstanceId, magazine[0].fieldValue]);
      } else if (this.isOpenAccess === true) {

        if (this.membership === false) {
            this.router.navigate(['/payment', this.processInstanceId, magazine[0].fieldValue]);
        } else if (this.membership === true) {
          this.router.navigate(['/workData', this.processInstanceId, magazine[0].fieldValue]);
        }
      }

    }, err => {
      alert('greska choose magazine!!!!!!!!!!');

    });
  }

  isActiveMembership(processInstanceId) {
      this.magazineService.isActiveMembership(processInstanceId).subscribe(res => {
        this.membership = res;
        console.log('MEMBERSHIP' + this.membership);

      }, err => {

        alert('GRESKA IS ACTIVE MEMBERSHIP!!!!!!!!!');

      });
  }

}
