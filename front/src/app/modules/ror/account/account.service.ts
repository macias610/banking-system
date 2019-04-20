import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError, of } from 'rxjs';
import { catchError, tap, map } from 'rxjs/operators';
import { Account } from '../../../models/account';
import { environment } from '../../../../environments/environment';
import { ResponseData } from '../../../models/responseData';


@Injectable({
    providedIn: 'root'
})
export class AccountService {

    constructor(private http: HttpClient) {
    }

    getAccounts(): Observable<ResponseData> {
        return this.http.get<ResponseData>(`${environment.api_url}/account/all`);
    }

    createAccount(account: any): Observable<ResponseData> {
        delete account.client;
        return this.http
            .post<ResponseData>(`${environment.api_url}/account/save`, account);
    }
}
