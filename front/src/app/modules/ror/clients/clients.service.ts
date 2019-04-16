import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError, of } from 'rxjs';
import { catchError, tap, map } from 'rxjs/operators';
import { Account } from '../../../models/account';
import { Client } from '../../../models/client/client';
import { environment } from '../../../../environments/environment';
import { ClientCreateDao } from '../../../models/client/clientCreateDao';
import { ResponseData } from '../../../models/responseData';

@Injectable({
    providedIn: 'root'
})
export class ClientsService {

    constructor(private http: HttpClient) {
    }

    getClients(): Observable<ResponseData> {
        return this.http.get<ResponseData>(`${environment.api_url}/client/clients`);
    }

    getClient(clientId: string): Observable<ResponseData> {
        return this.http.get<ResponseData>(`${environment.api_url}/client/${clientId}`);
    }

    createClient(client: ClientCreateDao): Observable<ResponseData> {
        return this.http
            .post<ResponseData>(`${environment.api_url}/client/save`, client);
    }
}
