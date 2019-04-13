import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError, of } from 'rxjs';
import { catchError, tap, map } from 'rxjs/operators';
import { Account } from '../../../models/account';
import { Client } from '../../../models/client/client';
import { environment } from '../../../../environments/environment';
import { ClientCreateDao } from '../../../models/client/clientCreateDao';


@Injectable({
    providedIn: 'root'
})
export class ClientsService {

    constructor(private http: HttpClient) {
    }

    getClients(): Observable<Client[]> {
        return this.http.get<Client[]>(`${environment.api_url}/clients`);
    }

    getClient(clientId: string): Observable<ClientCreateDao> {
        return this.http.get<ClientCreateDao>(`${environment.api_url}/client/${clientId}`);
    }

    createClient(client: ClientCreateDao): Observable<any> {
        return this.http
            .post(`${environment.api_url}/clients`, client);
    }
}
