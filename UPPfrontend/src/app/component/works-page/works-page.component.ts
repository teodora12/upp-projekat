import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {ToastrManager} from "ng6-toastr-notifications";
import {MagazineService} from "../../service/magazine.service";

@Component({
  selector: 'app-works-page',
  templateUrl: './works-page.component.html',
  styleUrls: ['./works-page.component.css']
})
export class WorksPageComponent implements OnInit {

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

  constructor(private router: Router, private toastr: ToastrManager, private magazineService: MagazineService) {

    this.processInstanceId = localStorage.getItem('processInstanceId');
    const user = JSON.parse(localStorage.getItem('loggedUser'));
    this.username = user.sub;
  }

  ngOnInit() {
    this.showWorks = true;
 //   this.getWorks(this.username);
    this.getFormFields();
  }


  getWorks(username) {
    console.log(username);
    this.magazineService.getWorksForMainRedactor(this.processInstanceId).subscribe( res => {
      this.work = res;

      console.log(res + 'rad');

    } );
  }

  submit(value, form) {
//    this.counter = this.counter + 1;
    const o = new Array();
    for (const property in value) {
      console.log(property);
      console.log(value[property]);
      o.push({fieldId : property, fieldValue : value[property]});
    }

    this.magazineService.submitMainRedactor(o, this.formFieldsDTO.taskId).subscribe( res => {

      this.toastr.successToastr('Succcess!');

      if ( res === true ) {
        localStorage.setItem('isAccepted_First', 'true');
        this.router.navigate(['/checkPdf']);
      } else if ( res === false) {
        localStorage.setItem('isAccepted_First', 'false');
        this.router.navigate(['/home']);
      }

    }, err => {
      alert('greska works page!!!!!!!');
    });

  }

  clickWork() {
    this.showWorks = false;
  }

  getFormFields() {
    this.magazineService.getFormFieldsMRedactor(this.processInstanceId).subscribe(formFieldsDTO => {
      this.task = formFieldsDTO;
      this.formFieldsDTO = formFieldsDTO;
      this.formFields = formFieldsDTO.formFields;

      console.log('formfildsssss');
      console.log(this.formFields)
      console.log('dtooo');
      console.log(formFieldsDTO);


    }, err => {
      alert('greska WORKS PAGE!!!!!!!!!!');
    });
  }

}
