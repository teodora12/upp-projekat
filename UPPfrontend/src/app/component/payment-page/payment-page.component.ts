import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {ToastrManager} from 'ng6-toastr-notifications';
import {MagazineService} from '../../service/magazine.service';

@Component({
  selector: 'app-payment-page',
  templateUrl: './payment-page.component.html',
  styleUrls: ['./payment-page.component.css']
})
export class PaymentPageComponent implements OnInit {

  private href: any;
  private processInstanceId;
  magazineTitle: any;
  private formFieldsDTO = null;
  private formFields = [];
  private processInstance = '';
  private enumValues = [];
  private tasks = [];


  constructor(private router: Router, private toastr: ToastrManager, private magazineService: MagazineService) {
    this.href = this.router.url;
//    this.processInstanceId = this.href.substring(this.href.lastIndexOf('/') + 1);
    this.processInstanceId = this.href.split('/')[2];
    this.magazineTitle = this.href.split('/')[3];


  }

  ngOnInit() {

    this.magazineService.getFormFieldsPayment(this.processInstanceId).subscribe(formFieldsDTO => {
          this.formFieldsDTO = formFieldsDTO;
          this.formFields = formFieldsDTO.formFields;
          this.processInstance = formFieldsDTO.processInstanceId;
          this.processInstanceId = formFieldsDTO.processInstanceId;
          this.formFields.forEach( (field) => {

                if ( field.type.name === 'enum') {
                  this.enumValues = Object.keys(field.type.values);
                }
              },
              err => {
                console.log('Error occured');
              }
          );


        }
    );
  }

  submitPayment(value, form) {

    const o = new Array();
    for (const property in value) {
      console.log(property);
      console.log(value[property]);
      o.push({fieldId : property, fieldValue : value[property]});
    }

    this.magazineService.submitPayment(this.formFieldsDTO.taskId, o).subscribe( res => {

      this.toastr.successToastr('Success!');
      this.router.navigate(['/workData', this.processInstanceId, this.magazineTitle]);
    }, err => {

      alert('greska payment!!!!!!!!!!!!!!');

    });

  }

}
