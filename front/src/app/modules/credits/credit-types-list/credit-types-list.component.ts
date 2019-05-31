import { Component, OnInit } from '@angular/core';
import { CreditsService } from '../credits.service';
import { CreditType } from '../../../models/credit/credit-type';
import { ResponseData } from '../../../models/responseData';
import { Notification } from '../../../models/notification';

@Component({
  selector: 'app-credit-types-list',
  templateUrl: './credit-types-list.component.html',
  styleUrls: ['./credit-types-list.component.scss']
})
export class CreditTypesListComponent implements OnInit {
  creditTypes: CreditType[];
  notification: Notification = new Notification();
  notificationTimer;

  constructor(private service: CreditsService) { }

  ngOnInit() {
    this.getCreditTypes();
    console.log("test");
  }


  getCreditTypes(): void {
    this.service.getActiveCreditTypes().subscribe((data: ResponseData) => {
      this.creditTypes = data['data'];
    },
    (error) => {
      this.creditTypes = [];
    });
  }

  deleteCreditType(creditTypeId: number): void {
    this.service.deleteCreditType(creditTypeId).subscribe(
      (data) => {
        this.addNotification(false, data.notification || '');
        this.getCreditTypes();
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
