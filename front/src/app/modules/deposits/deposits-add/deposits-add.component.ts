import {Component, OnInit} from '@angular/core';
import {DepositsService} from "../deposits.service";
import {AbstractControl, FormBuilder, FormGroup, ValidatorFn, Validators} from "@angular/forms";
import {ActivatedRoute} from '@angular/router';
import {Deposit} from "../../../models/deposit/deposit";
import {DepositType} from "../../../models/deposit/depositType";
import {formatDate} from "@angular/common";
import {ResponseData} from "../../../models/responseData";
import {Notification} from "../../../models/notification";

@Component({
  selector: 'app-deposits-add',
  templateUrl: './deposits-add.component.html',
  styleUrls: ['./deposits-add.component.scss']
})
export class DepositsAddComponent implements OnInit {

  formInSave = false;
  createDepositForm: FormGroup;
  accountId: number;
  depositTypes: DepositType[];
  notification: Notification = new Notification();
  notificationTimer;

  constructor(private fb: FormBuilder, private service: DepositsService, private route: ActivatedRoute) {}

  ngOnInit() {
    this.getDepositTypes();
    this.setAccountId();
    this.createAddDepositForm();
  }

  getDepositTypes(): void {
    this.service.getDepositTypes().subscribe(responseData =>
      this.depositTypes = responseData['data']
    );
  }

  setAccountId(): void {
    this.route.params.subscribe(params => {
      this.accountId = params['id'];
    });
  }

  createAddDepositForm(): void {
    this.createDepositForm = this.fb.group({
      'depositType': ['', [Validators.required]],
      'amount': ['', [Validators.required, this.correctAmountValidator()]],
      'maxAmount': '',
      'minAmount': '',
      'interestRate': '',
      'daysPeriod': '',
      'capitalizationType': ''
    });
  }

  correctAmountValidator(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } => {
      const group = control.parent;
      if (group) {
        const minAmountField = group.get('minAmount');
        const maxAmountField = group.get('maxAmount');
        const isAmountGreaterThanMinAmount = Number(minAmountField.value) <= Number(control.value);
        const isAmountLowerThanMaxAmount = Number(maxAmountField.value) >= Number(control.value);
        return !isAmountGreaterThanMinAmount || !isAmountLowerThanMaxAmount ? {incorrectAmount: true} : null;
      } else {
        return null;
      }
    }
  }

  onChange(selectedId: string) {
    const selectedDepositType: DepositType = this.depositTypes.filter(depositType => depositType.id == Number.parseInt(selectedId))[0];
    console.log(selectedDepositType);
    this.createDepositForm.controls['maxAmount'].setValue(selectedDepositType != undefined ? selectedDepositType.maxAmount : '');
    this.createDepositForm.controls['minAmount'].setValue(selectedDepositType != undefined ? selectedDepositType.minAmount : '');
    this.createDepositForm.controls['interestRate'].setValue(selectedDepositType != undefined ? selectedDepositType.interestRate  + ' %': '');
    this.createDepositForm.controls['daysPeriod'].setValue(selectedDepositType != undefined ? selectedDepositType.daysPeriod : '');
    this.createDepositForm.controls['capitalizationType'].setValue(selectedDepositType != undefined ? selectedDepositType.capitalizationType : '');
    this.createDepositForm.controls['amount'].setValue('');
  }

  addDeposit() {
    const formValue = this.createDepositForm.value;
    console.log(formValue);

    this.service.addDeposit(this.prepareDepositCreateDaoForRequest(formValue)).subscribe(
      (data: ResponseData) => {
        this.createDepositForm.reset();
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

  prepareDepositCreateDaoForRequest(formValue: any): Deposit {
    const todayDate: Date = new Date();
    const format = 'yyyy-MM-dd';
    const locale = 'en-US';
    const depositEndDate = new Date().setDate(todayDate.getDate() + formValue['daysPeriod']);
    const depositCreateDao = new Deposit(null, this.accountId, formValue['depositType'],
      formatDate(todayDate, format, locale), formatDate(depositEndDate, format, locale),
      Number.parseFloat(formValue['amount']), true);
    console.log(depositCreateDao);
    return depositCreateDao;
  }
}
