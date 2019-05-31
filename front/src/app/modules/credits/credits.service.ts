import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import {Observable, of} from 'rxjs';
import {environment} from "../../../environments/environment";
import {ResponseData} from "../../models/responseData";
import { CreditType } from '../../models/credit/credit-type';
import { Credit } from '../../models/credit/credit';

@Injectable({
  providedIn: 'root'
})
export class CreditsService {

  constructor(private http: HttpClient) { }

  addCredit(credit: Credit): Observable<ResponseData> {
    return this.http
      .post<ResponseData>(`${environment.api_url}/credit`, credit);
  }
  
  getActiveCreditTypes(): Observable<ResponseData> {
    return this.http
    .get<ResponseData>(`${environment.api_url}/creditType`)
  }
  
  getCreditTypes(): Observable<ResponseData> {
    return this.http
    .get<ResponseData>(`${environment.api_url}/creditType/all`);
  }
  
  getCredits(accountId: number): Observable<ResponseData> {
    return this.http.get<ResponseData>(`${environment.api_url}/credit/account/${accountId}`);
  }
  
  closeCredit(creditId: number): Observable<ResponseData> {
    return this.http.patch<ResponseData>(`${environment.api_url}/credit/${creditId}`, {});
  }
  
  remitCredit(creditId: number): Observable<ResponseData> {
    return this.http.patch<ResponseData>(`${environment.api_url}/credit/remit/${creditId}`, {});
  }
  
  addCreditType(creditType: CreditType): Observable<ResponseData> {
    return this.http
      .post<ResponseData>(`${environment.api_url}/creditType`, creditType);
  }

  deleteCreditType(creditTypeId: number): Observable<ResponseData> {
    return this.http.patch<ResponseData>(`${environment.api_url}/creditType/${creditTypeId}`, {});
  }

  getCreditBalances(): Observable<ResponseData> {
    return this.http
    .get<ResponseData>(`${environment.api_url}/creditBalance`);
  }

  getPaymentSchedules(creditId: number): Observable<ResponseData> {
    return this.http
      .get<ResponseData>(`${environment.api_url}/paymentSchedule/creditId/${creditId}`);
  }
}
