import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError, of } from 'rxjs';
import { catchError, tap, map } from 'rxjs/operators';
import { Account } from '../../../models/account';
import { Client } from '../../../models/client/client';


@Injectable({
    providedIn: 'root'
})
export class ClientsService {

    constructor(private http: HttpClient) {
    }

    getClients(): Observable<Client[]> {
        return this.http.get<Client[]>(`http://demo0783201.mockable.io/clients`);
    }
}
