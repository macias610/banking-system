import {Component, OnInit} from '@angular/core';
import {DepositsService} from "../deposits.service";
import {Deposit} from "../../../models/deposit/deposit";
import {ActivatedRoute} from "@angular/router";
import {DepositType} from "../../../models/deposit/depositType";
import {map} from "rxjs/operators";
import {Observable} from "rxjs";
import {Notification} from "../../../models/notification";

@Component({
  selector: 'app-deposits-list',
  templateUrl: './deposits-list.component.html',
  styleUrls: ['./deposits-list.component.scss']
})
export class DepositsListComponent implements OnInit {
  deposits$: Observable<Deposit[]>;
  depositTypes: DepositType[];
  accountId: number;
  notification: Notification = new Notification();
  notificationTimer;

  constructor(private service: DepositsService, private route: ActivatedRoute) {}

  ngOnInit() {
    this.setAccountId();
    this.getDepositTypes();
  }

  setAccountId(): void {
    this.route.params.subscribe(params => {
      this.accountId = params['id'];
    });
  }

  getDeposits(): void {
     this.deposits$ = this.service.getDeposits(this.accountId).pipe(
      map(item => item.data)
    );
  }

  getDepositTypes(): void {
    this.service.getDepositTypes().subscribe(responseData => {
        this.depositTypes = responseData['data'];
        this.getDeposits();
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
