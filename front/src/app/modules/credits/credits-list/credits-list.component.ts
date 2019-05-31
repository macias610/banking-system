import { Component, OnInit } from '@angular/core';
import { CreditsService } from '../credits.service';
import { ActivatedRoute } from '@angular/router';
import { Credit } from '../../../models/credit/credit';
import { CreditType } from '../../../models/credit/credit-type';
import { Notification } from '../../../models/notification';
import { ResponseData } from '../../../models/responseData';
import { CreditBalance } from '../../../models/credit/credit-balance';

@Component({
  selector: 'app-credits-list',
  templateUrl: './credits-list.component.html',
  styleUrls: ['./credits-list.component.scss']
})
export class CreditsListComponent implements OnInit {
  credits: Credit[];
  creditTypes: CreditType[];
  creditBalances: CreditBalance[];
  accountId: number;
  notification: Notification = new Notification();
  notificationTimer;
  test: number;

  constructor(private service: CreditsService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.setAccountData();
    this.getCreditBalances();
    this.getCreditTypes();
    console.log(this.creditBalances);
    
  }

  setAccountData(): void {
    this.route.params.subscribe(params => {
      this.accountId = params['id'];
    });
  }

  getCredits(): void {
    this.service.getCredits(this.accountId).subscribe((data: ResponseData) => {
      this.credits = data['data'];
      console.log(this.credits);
      // this.getCreditBalances();
    },
    (error) => {
      this.credits =[];
    })
  }

  getCreditTypes(): void {
    this.service.getCreditTypes().subscribe(responseData => {
      this.creditTypes = responseData['data'];
      console.log(this.creditTypes);
      this.getCredits();
      // this.getCreditBalances();
      },
      (error) => {
        this.creditTypes = [];
      },
    );
  }

  getCreditTypeName(creditTypeId: number): string {
    return this.creditTypes.filter(creditType => creditType.id === creditTypeId)[0].name;
  }

  getCreditTypeInterestRate(creditTypeId: number): number {
    return this.creditTypes.filter(creditType => creditType.id === creditTypeId)[0].interest_rate;
  }

  getCreditBalances(): void {
    this.service.getCreditBalances().subscribe(responseData => {
      this.creditBalances = responseData['data'];
      console.log(this.creditBalances);
      
    })
  }

  getCreditBalanceAssets(creditBalanceId: number): number {
    console.log(creditBalanceId);
    this.test = this.creditBalances.filter(creditBalance => creditBalance.id === creditBalanceId)[0].debt_asset;
    console.log(this.creditBalances[0].debt_asset);
    return this.test;
  }

  getCreditBalanceInterest(creditBalanceId: number): number {
    return this.creditBalances.filter(creditBalance => creditBalance.id === creditBalanceId)[0].debt_interest;
  }

  closeCredit(creditId: number): void {
    this.service.closeCredit(creditId).subscribe(
      (data) => {
        this.addNotification(false, data.notification || '');
        this.getCredits();
      }
    )
  }

  remitCredit(creditId: number): void {
    this.service.remitCredit(creditId).subscribe(
      (data) => {
        this.addNotification(false, data.notification || '');
        this.getCredits();
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
