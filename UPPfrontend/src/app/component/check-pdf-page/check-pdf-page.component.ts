import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {ToastrManager} from "ng6-toastr-notifications";
import {MagazineService} from "../../service/magazine.service";

@Component({
  selector: 'app-check-pdf-page',
  templateUrl: './check-pdf-page.component.html',
  styleUrls: ['./check-pdf-page.component.css']
})
export class CheckPdfPageComponent implements OnInit {

  private formFieldsDTO = null;
  private formFields = [];
  private processInstance = '';
  private formFieldValues;
  private enumValues = [];
  private processInstanceId;
  private username;
  work: any;
  showWorks: boolean;
  user: any;
  task: any;
  acceptedPdf: boolean;


  constructor(private router: Router, private toastr: ToastrManager, private magazineService: MagazineService) {

    this.processInstanceId = localStorage.getItem('processInstanceId');
    const user = JSON.parse(localStorage.getItem('loggedUser'));
    this.username = user.sub;
    this.acceptedPdf = false;
  }


  ngOnInit() {
    this.getFormFields();
  }


  getFormFields() {
    this.magazineService.getFormFieldsPayment(this.processInstanceId).subscribe(formFieldsDTO => {
      this.task = formFieldsDTO;
      this.formFieldsDTO = formFieldsDTO;
      this.formFields = formFieldsDTO.formFields;

      console.log('formfildsssss');
      console.log(this.formFields)
      console.log('dtooo');
      console.log(formFieldsDTO);


    }, err => {
      alert('greska check pdf PAGE!!!!!!!!!!');
    });
  }


  submit(value, form) {
//    this.counter = this.counter + 1;
    const o = new Array();
    for (const property in value) {
      console.log(property);
      console.log(value[property]);
      o.push({fieldId: property, fieldValue: value[property]});
    }

    this.magazineService.submitMainRedactorAcceptPdf(o, this.formFieldsDTO.taskId).subscribe( res => {
        this.acceptedPdf = res;

        if(this.acceptedPdf === false) {
            this.toastr.successToastr('Success!');
            this.router.navigate(['/mainRedactorComment']);
        } else if(this.acceptedPdf === true) {
            this.toastr.successToastr('Success!');
            this.router.navigate(['/home']);
        }

    }, err => {
        alert('greska check pdf!!!!!!!!!');
    });

  }

  }
