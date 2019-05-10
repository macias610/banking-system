import { Component, OnInit } from '@angular/core';
import { DepositType } from '../../../models/deposit/depositType';
import { Observable } from 'rxjs';
import { DepositsService } from '../deposits.service';
import {map} from "rxjs/operators";

@Component({
  selector: 'app-deposit-types-list',
  templateUrl: './deposit-types-list.component.html',
  styleUrls: ['./deposit-types-list.component.scss']
})
export class DepositTypesListComponent implements OnInit {
  depositTypes$: Observable<DepositType[]>;

  constructor(private service: DepositsService) { }

  ngOnInit() {
    this.getDepositTypes();
  }

  getDepositTypes(): void {
    this.depositTypes$ = this.service.getDepositTypes().pipe(
     map(item => item.data)
   );
 }
}
