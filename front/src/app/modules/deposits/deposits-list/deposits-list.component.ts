import {Component, OnInit} from '@angular/core';
import {DepositsService} from "../deposits.service";
import {Deposit} from "../../../models/deposit/deposit";
import {ActivatedRoute} from "@angular/router";
import {DepositType} from "../../../models/deposit/depositType";
import {Notification} from "../../../models/notification";
import { ResponseData } from '../../../models/responseData';

@Component({
  selector: 'app-deposits-list',
  templateUrl: './deposits-list.component.html',
  styleUrls: ['./deposits-list.component.scss']
})
export class DepositsListComponent implements OnInit {
  deposits: Deposit[];
  depositTypes: DepositType[];
  accountId: number;
  currency: string;
  notification: Notification = new Notification();
  notificationTimer;

  constructor(private service: DepositsService, private route: ActivatedRoute) {}

  ngOnInit() {
    this.setAccountData();
    this.getDepositTypes();
  }

  setAccountData(): void {
    this.route.params.subscribe(params => {
      this.accountId = params['id'];
      this.currency = params['currency'];
    });
  }

  getDeposits(): void {
    this.service.getDeposits(this.accountId).subscribe((data: ResponseData) => {
      this.deposits = data['data'];
      console.log(this.deposits);
    }, 
    (error) => {
      this.deposits = [];
    })
  }

  getDepositTypes(): void {
    this.service.getDepositTypes().subscribe(responseData => {
        this.depositTypes = responseData['data'];
        this.getDeposits();
      },
      (error) => {
        this.depositTypes = [];
      }
    );
  }

  getDepositTypeName(depositTypeId: number): string {
    return this.depositTypes.filter(depositType => depositType.id === depositTypeId)[0].name;
  }

  getDepositTypeInterestRate(depositTypeId: number): number {
    return this.depositTypes.filter(depositType => depositType.id === depositTypeId)[0].interestRate;
  }

  closeDeposit(depositId: number): void {
    this.service.closeDeposit(depositId).subscribe(
      (data) => {
        this.addNotification(false, data.notification || '');
        this.getDeposits();
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
}
