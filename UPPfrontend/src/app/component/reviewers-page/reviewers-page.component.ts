import { Component, OnInit } from '@angular/core';
import {UserService} from '../../service/user.service';
import {ToastrManager} from 'ng6-toastr-notifications';
import {Router} from '@angular/router';

@Component({
  selector: 'app-reviewers-page',
  templateUrl: './reviewers-page.component.html',
  styleUrls: ['./reviewers-page.component.css']
})
export class ReviewersPageComponent implements OnInit {

  private request: any;
  private formFieldsDTO = null;
  private formFields = [];
  private processInstance = '';
  private enumValues = [];
  private tasks = [];
  private processInstanceId;
  taskId = '';

  constructor(private userService: UserService, private toastr: ToastrManager, private router: Router) { }

  ngOnInit() {
    this.getRequests();

  }

  submitTask(value, form) {

    const o = new Array();
    for (const property in value) {
      console.log(property);
      console.log(value[property]);
      o.push({fieldId : property, fieldValue : value[property]});
    }

    console.log(o);

    this.userService.submitReviewer(o, this.formFieldsDTO.taskId).subscribe( res => {
          console.log(res);
          this.toastr.successToastr('Submited', 'Success');

        }, err => {
          console.log('Error occured submit');

        }
    );




  }

  getRequests() {

    const x = this.userService.getReviewerRequests();

    x.subscribe(
        res => {
          console.log(res);
          this.taskId = res.taskId;
          this.formFieldsDTO = res;
          this.formFields = res.formFields;

          // this.processInstanceId = res.processInstanceId;

        },
        err => {
          console.log('Error occured');
        }
    );

  }



}
