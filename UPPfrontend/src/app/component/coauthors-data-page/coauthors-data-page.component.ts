import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {ToastrManager} from "ng6-toastr-notifications";
import {MagazineService} from "../../service/magazine.service";

@Component({
  selector: 'app-coauthors-data-page',
  templateUrl: './coauthors-data-page.component.html',
  styleUrls: ['./coauthors-data-page.component.css']
})
export class CoauthorsDataPageComponent implements OnInit {

  href: any;
  private formFieldsDTO = null;
  private formFields = [];
  private processInstance = '';
  private enumValues = [];
  numOfCoauthors: number;
  private processInstanceId;
  counter = 0;

  constructor(private router: Router, private toastr: ToastrManager, private magazineService: MagazineService) {
    this.href = this.router.url;
    this.processInstanceId = this.href.split('/')[3];
    this.numOfCoauthors = this.href.split('/')[2];
  }

  ngOnInit() {
    this.getFormFields();
  }

  getFormFields() {
    this.magazineService.getFormFieldsPayment(this.processInstanceId).subscribe(formFieldsDTO => {
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
      alert('greska COAUTHORS DATA!!!!!!!!!!');
    });
  }

  submit(value, form) {

    this.counter = this.counter + 1;
    const o = new Array();
    for (const property in value) {
      console.log(property);
      console.log(value[property]);
      o.push({fieldId : property, fieldValue : value[property]});
    }
    console.log(o);

    this.magazineService.submitCoauthorData(o, this.formFieldsDTO.taskId).subscribe(res => {
      console.log(res);

      if (this.counter < this.numOfCoauthors) {
        this.getNextTask();
      } else {
        this.toastr.successToastr('Success!');
        this.router.navigate(['/home']);
      }

    }, err => {
      alert('greska kod submitovanja naucne oblasti!');
    });

  }

  getNextTask() {
    this.magazineService.getFormFieldsPayment(this.processInstanceId).subscribe(formFieldsDTO => {
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
      alert('greska kod getovanja polja next taska!');
    });
  }

}
