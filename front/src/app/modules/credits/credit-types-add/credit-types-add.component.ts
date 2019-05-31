import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { Notification } from '../../../models/notification';
import { CreditType } from '../../../models/credit/credit-type';
import { CreditsService } from '../credits.service';
import { ResponseData } from '../../../models/responseData';

@Component({
  selector: 'app-credit-types-add',
  templateUrl: './credit-types-add.component.html',
  styleUrls: ['./credit-types-add.component.scss']
})
export class CreditTypesAddComponent implements OnInit {

  formInSave = false;
  createCreditTypeForm: FormGroup;
  accountId: number;
  currency: string;
  notification: Notification = new Notification();
  notificationTimer;
  loanPeriods = [];

  constructor(private fb: FormBuilder, private service: CreditsService) {}

  ngOnInit() {
    this.createAddCreditTypeForm();
    this.loanPeriods = [];
    this.loanPeriods.push(3);
    this.loanPeriods.push(6);
    this.loanPeriods.push(9);
    this.loanPeriods.push(12);
    this.loanPeriods.push(24);
    this.loanPeriods.push(36);
    this.loanPeriods.push(48);
    this.loanPeriods.push(60);
    this.loanPeriods.push(72);
    this.loanPeriods.push(84);
    this.loanPeriods.push(96);
    this.loanPeriods.push(108);
    this.loanPeriods.push(120);
  }

  createAddCreditTypeForm(): void {
    this.createCreditTypeForm = this.fb.group({
      'name': ['', [Validators.required]],
      'info': ['', [Validators.required]],
      'minValue': ['', [Validators.required, Validators.min(20000), Validators.max(2000000)]],
      'maxValue': ['', [Validators.required, Validators.min(20000), Validators.max(2000000)]],
      'interestRate': ['', [Validators.required, Validators.min(1), Validators.max(10)]],
      'loanPeriod': ['', [Validators.required]]
    });
  }

  correctMinAndMaxAmountValidator(formControl: FormControl): any {
    if (formControl.parent) {
      if (parseFloat(formControl.parent.get("minValue").value) < parseFloat(formControl.value)) {
        return null;
      } else {
        return { correct: false };
      }
    }
    return null;
  }

  addCreditType() {
    const formValue = this.createCreditTypeForm.value;
    console.log(formValue);

    this.service.addCreditType(this.prepareCreditTypeCreateDaoForRequest(formValue)).subscribe(
      (data: ResponseData) => {
        this.createCreditTypeForm.reset();
        this.formInSave = true;
        this.addNotification(false, data.notification || '');
        console.log(data);
      },
      (error) => {
        const errorData: ResponseData = error.error;
        this.formInSave = true;
        this.addNotification(true, errorData ? errorData.notification : '');
        console.log(errorData);
      }
    )
  }

  addNotification(error: boolean, msg: string) {
    this.notification.message = msg;
    this.notification.error = error;
    if (this.notificationTimer) {
      clearTimeout(this.notificationTimer);
    }

    this.notificationTimer = setTimeout(() => {
      this.notification = new Notification();
    }, 3000);
  }

  prepareCreditTypeCreateDaoForRequest(formValue: any): CreditType {
    const creditTypeCreateDao = new CreditType(null, formValue['name'], formValue['info'],
    formValue['minValue'], formValue['maxValue'], formValue['loanPeriod'], formValue['interestRate']);
    console.log(creditTypeCreateDao);
    return creditTypeCreateDao;
  }

  get name() { return this.createCreditTypeForm.controls['name']; }
  get info() { return this.createCreditTypeForm.controls['info']; }
  get minValue() { return this.createCreditTypeForm.controls['minValue']; }
  get maxValue() { return this.createCreditTypeForm.controls['maxValue']; }
  get interestRate() { return this.createCreditTypeForm.controls['interestRate']; }
  get loanPeriod() { return this.createCreditTypeForm.controls['loanPeriod']; }

}
