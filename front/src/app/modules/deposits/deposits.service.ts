import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {environment} from "../../../environments/environment";
import {Deposit} from "../../models/deposit/deposit";
import {DepositType} from '../../models/deposit/depositType';
import {ResponseData} from "../../models/responseData";

@Injectable({
  providedIn: 'root'
})
export class DepositsService {

  constructor(private http: HttpClient) {
  }

  addDeposit(deposit: Deposit): Observable<ResponseData> {
    return this.http
      .post<ResponseData>(`${environment.api_url}/deposit/`, deposit);
  }

  getActiveDepositTypes(): Observable<ResponseData> {
    return this.http
      .get<ResponseData>(`${environment.api_url}/deposit-types`);
  }

  getDepositTypes(): Observable<ResponseData> {
    return this.http
      .get<ResponseData>(`${environment.api_url}/deposit-types/all`);
  }

  getDeposits(accountId: number): Observable<ResponseData> {
    return this.http.get<ResponseData>(`${environment.api_url}/deposit/account/${accountId}`);
  }

  closeDeposit(depositId: number): Observable<ResponseData> {
    return this.http.patch<ResponseData>(`${environment.api_url}/deposit/${depositId}`, {});
  }

  addDepositType(depositType: DepositType): Observable<ResponseData> {
    return this.http
      .post<ResponseData>(`${environment.api_url}/deposit-types/`, depositType);
  }

  deleteDepositType(depositTypeId: number): Observable<ResponseData> {
    return this.http.patch<ResponseData>(`${environment.api_url}/deposit-types/${depositTypeId}`, {});
  }

  getCapitalizationTypes(): Observable<ResponseData> {
    return this.http.get<ResponseData>(`${environment.api_url}/capitalization`);
  }
}
