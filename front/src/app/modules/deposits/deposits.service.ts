import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {environment} from "../../../environments/environment";
import {DepositCreateDao} from "../../models/deposit/depositCreateDao";
import {DepositType} from '../../models/deposit/depositType';

@Injectable({
  providedIn: 'root'
})
export class DepositsService {

  constructor(private http: HttpClient) {
  }

  addDeposit(deposit: DepositCreateDao): Observable<DepositCreateDao> {
    return this.http
      .post<DepositCreateDao>(`${environment.api_url}/deposit/add`, deposit);
  }

  getDepositTypes(): Observable<DepositType[]> {
    return of([
      new DepositType(1, 'Deposit 1', 2000, 1000, 2.5, 90),
      new DepositType(2, 'Deposit 2', 3000, 1000, 2.5, 90),
      new DepositType(3, 'Deposit 3', 4000, 1000, 2.5, 90),
      new DepositType(4, 'Deposit 4', 5000, 1000, 2.5, 90),
    ]);
    // return this.http
    //   .get<DepositType[]>(`${environment.api_url}/deposit/types`);
  }
}
