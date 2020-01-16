import { Component, OnInit } from '@angular/core';
import {UserService} from '../../service/user.service';
import {Router} from '@angular/router';
import {ToastrManager} from "ng6-toastr-notifications";

@Component({
  selector: 'app-registration-page',
  templateUrl: './registration-page.component.html',
  styleUrls: ['./registration-page.component.css']
})
export class RegistrationPageComponent implements OnInit {

  user: any;
  private formFieldsDTO = null;
  private formFields = [];
  private processInstance = '';
  private enumValues = [];
  private tasks = [];

  constructor(private userService: UserService, private router: Router, private toastr: ToastrManager) {

    this.user = {name: '', lastname: '', email: '', password: '', state: '', city: '', title: '', isReviewer: false,
      numOfScientificFields: ''};

  }

  ngOnInit() {
    this.getFields();
  }

  register(value, form) {

      const o = new Array();
      for (const property in value) {
          console.log(property);
          console.log(value[property]);
          o.push({fieldId : property, fieldValue : value[property]});
      }

      this.user.name = value["name"];
      this.user.lastname = value["lastname"];
      this.user.email = value["email"];
      this.user.password = value["password"];
      this.user.state = value["state"];
      this.user.city = value["city"];
      this.user.title = value["title"];
      this.user.isReviewer = value["reviewer"];
      this.user.numOfScientificFields = value["scientificFields"];

      console.log(o);
/*
      const x = this.userService.register(this.user);

      x.subscribe(
          res => {
              console.log(res);

              console.log('You registered successfully!');
          },
          err => {
              console.log('Error occured register');
          }
      );
*/
      this.userService.submitTask(o, this.formFieldsDTO.taskId).subscribe( res => {
          console.log(res);
          this.toastr.successToastr('You registered successfully!', 'Success');
      }, err => {
              console.log('Error occured submit');

          }
      );

  }

  getFields() {
    this.userService.getFormFields().subscribe( formFieldsDTO => {
      this.formFieldsDTO = formFieldsDTO;
      this.formFields = formFieldsDTO.formFields;
      this.processInstance = formFieldsDTO.processInstanceId;

      this.formFields.forEach( (field) => {

        if ( field.type.name === 'enum') {
          this.enumValues = Object.keys(field.type.values);
        }
      },
          err => {
            console.log('Error occured');
          }
      );


    });
  }

}
