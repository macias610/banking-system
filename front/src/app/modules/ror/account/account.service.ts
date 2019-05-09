import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
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

    getAccount(accountId: String): Observable<ResponseData> {
        return this.http.get<ResponseData>(`${environment.api_url}/account/${accountId}`);
    }

    getAccountsForClient(clientId: String): Observable<ResponseData> {
        return this.http.get<ResponseData>(`${environment.api_url}/account/client/${clientId}`);
    }

    createAccount(account: any): Observable<ResponseData> {
        delete account.client;
        return this.http
            .post<ResponseData>(`${environment.api_url}/account/save`, account);
    }

    addAgent(data: any): Observable<ResponseData> {
        return this.http
            .post<ResponseData>(`${environment.api_url}/account/agent`, data);
    }

    removeAgent(accountId: any): Observable<ResponseData> {
        return this.http
            .delete<ResponseData>(`${environment.api_url}/account/${accountId}/agent`);
    }

    changeAccountStatus(accountId: string): Observable<ResponseData> {
        return this.http
            .patch<ResponseData>(`${environment.api_url}/account/state/${accountId}`, {});
    }

    removeAccount(accountId: string): Observable<ResponseData> {
        return this.http
            .patch<ResponseData>(`${environment.api_url}/account/delete/${accountId}`, {});
    }

    changeAmountOfAccount(type: string, amount: number, accountId: string): Observable<ResponseData> {
        const data = {
            type: type,
            value: amount,
            account_id: accountId
        };

        return this.http
            .post<ResponseData>(`${environment.api_url}/transaction/overflow`, data);
    }
}
