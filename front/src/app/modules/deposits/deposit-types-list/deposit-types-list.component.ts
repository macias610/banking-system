import { Component, OnInit } from '@angular/core';
import { DepositType } from '../../../models/deposit/depositType';
import { Observable } from 'rxjs';
import { DepositsService } from '../deposits.service';
import { map } from "rxjs/operators";
import { Notification } from "../../../models/notification";
import { ResponseData } from '../../../models/responseData';

@Component({
  selector: 'app-deposit-types-list',
  templateUrl: './deposit-types-list.component.html',
  styleUrls: ['./deposit-types-list.component.scss']
})
export class DepositTypesListComponent implements OnInit {
  depositTypes: DepositType[];
  notification: Notification = new Notification();
  notificationTimer;

  constructor(private service: DepositsService) { }

  ngOnInit() {
    this.getDepositTypes();
  }

  getDepositTypes(): void {
    this.service.getDepositTypes().subscribe((data: ResponseData) => {
      this.depositTypes = data['data'];
    },
    (error) => {
      this.depositTypes = [];
    });
 }

  deleteDepositType(depositTypeId: number): void {
    this.service.deleteDepositType(depositTypeId).subscribe(
      (data) => {
        this.addNotification(false, data.notification || '');
        this.getDepositTypes();
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
