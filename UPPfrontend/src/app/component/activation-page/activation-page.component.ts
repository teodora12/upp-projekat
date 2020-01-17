import { Component, OnInit } from '@angular/core';
import {UserService} from '../../service/user.service';
import {Router} from '@angular/router';
import {ToastrManager} from 'ng6-toastr-notifications';

@Component({
  selector: 'app-activation-page',
  templateUrl: './activation-page.component.html',
  styleUrls: ['./activation-page.component.css']
})
export class ActivationPageComponent implements OnInit {

  private href: any;
  private email: any;
  private processInstanceId: any;
  private activate: boolean;
  private formFieldsDTO = null;
  private formFields = [];
  private processInstance = '';
  private enumValues = [];

  constructor(private userService: UserService, private router: Router, private toastr: ToastrManager) {
    this.href = this.router.url;
//    this.email = this.href.substring(this.href.lastIndexOf('/') + 1);
    this.email = this.href.split('/')[2];
    this.processInstanceId = this.href.split('/')[3];

    console.log(this.processInstanceId);
    console.log(this.email);
  }

  ngOnInit() {
    this.getTask();


  }

  getTask() {
    this.userService.getActivationFormFields(this.processInstanceId).subscribe(formFieldsDTO => {
      this.formFieldsDTO = formFieldsDTO;
      this.formFields = formFieldsDTO.formFields;
      this.processInstance = formFieldsDTO.processInstanceId;
      this.processInstanceId = formFieldsDTO.processInstanceId;
      this.formFields.forEach( (field) => {

        if ( field.type.name === 'enum') {
          this.enumValues = Object.keys(field.type.values);
        }
      });
      this.toastr.infoToastr('Fill in the checkbox and click on the button to activate your account.');
    }, err => {
      alert('greska pri dobavljanju polja za aktivaciju naloga!');
    });
  }

  activateAccount(value, form) {

    const o = new Array();
    for (const property in value) {
      console.log(property);
      console.log(value[property]);
      o.push({fieldId : property, fieldValue : value[property]});
    }
    console.log(o);

    this.userService.activateAccount(o, this.formFieldsDTO.taskId).subscribe(res => {

      this.toastr.successToastr('You successfully activated your account, now you can log in!', 'Success');
      this.router.navigate(['/login']);

    }, err => {
      alert('greska kod submitovanja aktivacije akaunta!');
    });
  }

}
