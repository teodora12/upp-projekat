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

  constructor( private router: Router, private toastr: ToastrManager, private magazineService: MagazineService) { }

  ngOnInit() {

    this.startProcess();
  }

  startProcess() {
    this.magazineService.startProcess().subscribe(formFieldsDTO => {
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


    });


  }

  getMagazines(processInstanceId) {
    this.magazineService.getMagazines(processInstanceId).subscribe(magazines => {
      this.magazines = magazines;

      this.magazines.forEach( (field) => {
        this.enumValues.push(field.title);
        console.log(field.title);
        console.log(field.id);

          //      this.enumValues = Object.keys(field['title']);
        console.log(this.enumValues);

      } );

    });
  }

  choose(value, form) {

    console.log(this.choosenMagazine);

    const o = new Array();
    for (const property in value) {
          console.log(property);
          console.log(value[property]);
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

      if (this.isOpenAccess === false) {
        this.router.navigate(['/workData', this.processInstanceId]);
      }

    }, err => {
      alert('greska!!!!!!!!!!');

    });
  }


}
