import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {ToastrManager} from 'ng6-toastr-notifications';
import {MagazineService} from '../../service/magazine.service';

@Component({
  selector: 'app-work-data-page',
  templateUrl: './work-data-page.component.html',
  styleUrls: ['./work-data-page.component.css']
})
export class WorkDataPageComponent implements OnInit {

  private href: any;
  private formFieldsDTO = null;
  private formFields = [];
  private processInstance = '';
  private enumValues = [];
  private tasks = [];
  private processInstanceId;
  scientificFields: any;
  magazineTitle: any;
  choosenScientificField: any;

  constructor(private router: Router, private toastr: ToastrManager, private magazineService: MagazineService) {
    this.href = this.router.url;
//    this.processInstanceId = this.href.substring(this.href.lastIndexOf('/') + 1);
    this.processInstanceId = this.href.split('/')[2];
    this.magazineTitle = this.href.split('/')[3];
  }

  ngOnInit() {
    this.getFormFields(this.processInstanceId);
  }

  getFormFields(processInstanceId) {
    this.magazineService.getFormFields(processInstanceId).subscribe(formFieldsDTO => {
      console.log(formFieldsDTO);
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

      this.getScientificFieldsForMagazine(this.magazineTitle);


    });
  }

  getScientificFieldsForMagazine(magazineTitle) {
    this.magazineService.getScientificFieldsForMagazine(magazineTitle).subscribe(scientificFields => {
      this.scientificFields = scientificFields;

      this.scientificFields.forEach( (field) => {
        this.enumValues.push(field.name);
        console.log(field.name);
        console.log(field.id);

        console.log(this.enumValues);

      } );

    });
  }

  submitForm(value, form) {

    console.log(this.choosenScientificField);

  }

}
