import {Component, OnInit} from '@angular/core';
import {DepositsService} from "../deposits.service";
import {Deposit} from "../../../models/deposit/deposit";
import {ActivatedRoute} from "@angular/router";
import {DepositType} from "../../../models/deposit/depositType";

@Component({
  selector: 'app-deposits-list',
  templateUrl: './deposits-list.component.html',
  styleUrls: ['./deposits-list.component.scss']
})
export class DepositsListComponent implements OnInit {
  deposits: Deposit[];
  depositTypes: DepositType[];
  accountId: number;

  constructor(private service: DepositsService, private route: ActivatedRoute) {}

  ngOnInit() {
    this.setAccountId();
    this.getDepositTypes();
    this.getDeposits();
  }

  setAccountId(): void {
    this.route.params.subscribe(params => {
      this.accountId = params['id'];
    });
  }

  getDeposits(): void {
    this.service.getDeposits(this.accountId).subscribe(deposits => {
        this.deposits = deposits;
      },
      (error) => {
        console.log(error);
        this.deposits = [];
      });
  }

  getDepositTypes(): void {
    this.service.getDepositTypes().subscribe(
      (depositTypes: DepositType[]) => {
        this.depositTypes = depositTypes
      },
      (error) => {
        console.log(error);
      }
    );
  }

  getDepositTypeName(depositTypeId: number): string {
    return this.depositTypes.filter(depositType => depositType.id === depositTypeId)[0].name;
  }

  getDepositTypeInterestRate(depositTypeId: number): number {
    return this.depositTypes.filter(depositType => depositType.id === depositTypeId)[0].interestRate;
  }
}
