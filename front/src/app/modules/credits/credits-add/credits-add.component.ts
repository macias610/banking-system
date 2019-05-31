import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ValidatorFn, AbstractControl } from '@angular/forms';
import { CreditType } from '../../../models/credit/credit-type';
import { Notification } from '../../../models/notification';
import { CreditsService } from '../credits.service';
import { ActivatedRoute } from '@angular/router';
import { Credit } from '../../../models/credit/credit';
import { ResponseData } from '../../../models/responseData';

@Component({
  selector: 'app-credits-add',
  templateUrl: './credits-add.component.html',
  styleUrls: ['./credits-add.component.scss']
})
export class CreditsAddComponent implements OnInit {

  formInSave = false;
  createCreditForm: FormGroup;
  accountId: number;
  creditTypes: CreditType[];
  notification: Notification = new Notification();
  notificationTimer;

  constructor(private fb: FormBuilder, private service: CreditsService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.getCreditTypes();
    this.setAccountId();
    this.createAddCreditForm();
  }

  getCreditTypes(): void {
    this.service.getActiveCreditTypes().subscribe(responseData => {
      this.creditTypes = responseData['data'];
      console.log(this.creditTypes);
      
      }
    );
  }

  setAccountId(): void {
    this.route.params.subscribe(params => {
      this.accountId = params['id'];
    });
  }

  createAddCreditForm(): void {
    this.createCreditForm = this.fb.group({
      'creditType': ['', [Validators.required]],
      'creditInfo': {value: '', disabled: true},
      'minValue': {value: '', disabled: true},
      'maxValue': {value: '', disabled: true},
      'interestRate': {value: '', disabled: true},
      'loanPeriod': {value: '', disabled: true},
      'value': ['', [Validators.required, this.correctValueValidator()]],
    });
  }

  correctValueValidator(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } => {
      const group = control.parent;
      if(group) {
        const minValueField = group.get('minValue');
        const maxValueField = group.get('maxValue');
        const isValueGreaterThanMinValue = Number(minValueField.value) <= Number(control.value);
        const isValueLowerThanMaxValue = Number(maxValueField.value) >= Number(control.value);
        return !isValueGreaterThanMinValue || !isValueLowerThanMaxValue ? {incorrectValue: true} : null;
      } else {
        return null;
      }
    }
  }

  onChange(selectedId: number) {
    const selectedCreditType: CreditType = this.creditTypes.filter(creditType => creditType.id == selectedId)[0];
    console.log(selectedCreditType);
    this.createCreditForm.controls['creditInfo'].setValue(selectedCreditType != undefined ? selectedCreditType.info : '');
    this.createCreditForm.controls['minValue'].setValue(selectedCreditType != undefined ? selectedCreditType.min_value : '');
    this.createCreditForm.controls['maxValue'].setValue(selectedCreditType != undefined ? selectedCreditType.max_value : '');
    this.createCreditForm.controls['interestRate'].setValue(selectedCreditType != undefined ? selectedCreditType.interest_rate + ' %': '');
    this.createCreditForm.controls['loanPeriod'].setValue(selectedCreditType != undefined ? selectedCreditType.loan_period : '');
    this.createCreditForm.controls['value'].setValue('');    
  }

  addCredit() {
    const formValue = this.createCreditForm.value;
    console.log(formValue);

    this.service.addCredit(this.prepareCreditCreateDaoForRequiest(formValue)).subscribe(
      (data: ResponseData) => {
        this.createCreditForm.reset();
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

  prepareCreditCreateDaoForRequiest(formValue: any): any {
    const creditCreateDao = new Credit(null, this.accountId, formValue['creditType'], 
      null, formValue['value'], true, null, null);
      console.log(creditCreateDao);
      return creditCreateDao;
  }

  get value() {return this.createCreditForm.controls['value'];}
  get creditType() {return this.createCreditForm.controls['creditType'];}
  
}
