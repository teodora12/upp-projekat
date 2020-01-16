import { Component, OnInit } from '@angular/core';
import {UserService} from '../../service/user.service';
import {Router} from '@angular/router';
import {ToastrManager} from 'ng6-toastr-notifications';

@Component({
  selector: 'app-scientific-fields-page',
  templateUrl: './scientific-fields-page.component.html',
  styleUrls: ['./scientific-fields-page.component.css']
})
export class ScientificFieldsPageComponent implements OnInit {

  href: any;
  private formFieldsDTO = null;
  private formFields = [];
  private processInstance = '';
  private enumValues = [];
  numOfScientificFields: number;
  private processInstanceId;


  constructor(private userService: UserService, private router: Router, private toastr: ToastrManager) {
    this.href = this.router.url;
    this.processInstanceId = this.href.split('/')[3];
    this.numOfScientificFields = this.href.split('/')[2];

  }

  ngOnInit() {
    this.getFormFields();

  }

  getFormFields() {
    this.userService.getFormScientificFields(this.processInstanceId).subscribe(formFieldsDTO => {
      this.formFieldsDTO = formFieldsDTO;
      this.formFields = formFieldsDTO.formFields;
      this.processInstance = formFieldsDTO.processInstanceId;
      this.processInstanceId = formFieldsDTO.processInstanceId;
      this.formFields.forEach( (field) => {

        if ( field.type.name === 'enum') {
          this.enumValues = Object.keys(field.type.values);
        }
      });

    }, err => {
      alert('greska kod get form fields za naucne oblasti!');
    });
  }

  submit(value, form) {

    const o = new Array();
    for (const property in value) {
      console.log(property);
      console.log(value[property]);
      o.push({fieldId : property, fieldValue : value[property]});
    }
    console.log(o);

    this.userService.submitScientificFields(o, this.formFieldsDTO.taskId).subscribe(res => {
      console.log(res);

      this.getNextTask();

    }, err => {
      alert('greska kod submitovanja naucne oblasti!');
    });

  }

  getNextTask() {
    this.userService.getFormScientificFields(this.processInstanceId).subscribe(formFieldsDTO => {
      console.log(formFieldsDTO);
      this.formFieldsDTO = formFieldsDTO;
      this.formFields = formFieldsDTO.formFields;
      this.processInstance = formFieldsDTO.processInstanceId;
      this.processInstanceId = formFieldsDTO.processInstanceId;
      this.formFields.forEach( (field) => {

        if ( field.type.name === 'enum') {
          this.enumValues = Object.keys(field.type.values);
        }
      });

    }, err => {
      alert('greska kod getovanja polja next taska!')
    });
  }

}
