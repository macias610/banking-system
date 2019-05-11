import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { DepositsService } from '../deposits.service';
import { ResponseData } from '../../../models/responseData';
import { Notification } from "../../../models/notification";
import { DepositType } from '../../../models/deposit/depositType';
import { DepositCapitalization } from '../../../models/deposit/depositCapitalization';

@Component({
  selector: 'app-deposit-types-add',
  templateUrl: './deposit-types-add.component.html',
  styleUrls: ['./deposit-types-add.component.scss']
})
export class DepositTypesAddComponent implements OnInit {

  formInSave = false;
  createDepositTypeForm: FormGroup;
  accountId: number;
  currency: string;
  notification: Notification = new Notification();
  notificationTimer;
  capitalizationTypes: DepositCapitalization[];

  constructor(private fb: FormBuilder, private service: DepositsService) {}

  ngOnInit() {
    this.createAddDepositTypeForm();
    this.getCapitalizationTypes();
  }

  createAddDepositTypeForm(): void {
    this.createDepositTypeForm = this.fb.group({
      'name': ['', [Validators.required]],
      'minAmount': ['', [Validators.required, Validators.min(500), Validators.max(500000)]],
      'maxAmount': ['', [Validators.required, Validators.min(500), Validators.max(500000), this.correctMinAndMaxAmountValidator]],
      'interestRate': ['', [Validators.required, Validators.min(0.1), Validators.max(10)]],
      'daysPeriod': ['', [Validators.required, Validators.min(30), Validators.max(1000)]],
      'capitalizationType': ['', [Validators.required]],
    });
  }

  correctMinAndMaxAmountValidator(formControl: FormControl): any {
    if (formControl.parent) {
      if (parseFloat(formControl.parent.get("minAmount").value) < parseFloat(formControl.value)) {
        return null;
      } else {
        return { correct: false };
      }
    }
    return null;
  }
   

  addDepositType() {
    const formValue = this.createDepositTypeForm.value;
    console.log(formValue);

    this.service.addDepositType(this.prepareDepositTypeCreateDaoForRequest(formValue)).subscribe(
      (data: ResponseData) => {
        this.createDepositTypeForm.reset();
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
    );
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

  prepareDepositTypeCreateDaoForRequest(formValue: any): DepositType {
    const depositTypeCreateDao = new DepositType(null, formValue['name'], formValue['minAmount'], formValue['maxAmount'],
    formValue['interestRate'], formValue['daysPeriod'], formValue['capitalizationType']);
    console.log(depositTypeCreateDao);
    return depositTypeCreateDao;
  }

  getCapitalizationTypes(): void {
    this.service.getCapitalizationTypes().subscribe(
      (responseDate: ResponseData) => {
        this.capitalizationTypes = responseDate['data'];
      }
    );
  }

  get name() { return this.createDepositTypeForm.controls['name']; }
  get minAmount() { return this.createDepositTypeForm.controls['minAmount']; }
  get maxAmount() { return this.createDepositTypeForm.controls['maxAmount']; }
  get interestRate() { return this.createDepositTypeForm.controls['interestRate']; }
  get daysPeriod() { return this.createDepositTypeForm.controls['daysPeriod']; }
  get capitalizationType() { return this.createDepositTypeForm.controls['capitalizationType']; }
}
