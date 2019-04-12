import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError, of } from 'rxjs';
import { catchError, tap, map } from 'rxjs/operators';
import { Account } from '../../../models/account';


@Injectable({
    providedIn: 'root'
})
export class AccountService {

    constructor(private http: HttpClient) {
    }

    getAccounts(): Observable<Account[]> {
        return this.http.get<Account[]>(`http://demo0783201.mockable.io/accounts`);
    }
}
