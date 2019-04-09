import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError, of } from 'rxjs';
import { catchError, tap, map } from 'rxjs/operators';

export interface Account {
    id: number;
    userId: number;
    title: string;
    body: string;
}

@Injectable({
    providedIn: 'root'
})
export class AccountService {

    constructor(private http: HttpClient) {
    }

    getAccounts(): Observable<Account[]> {
        return this.http.get<Account[]>(`https://jsonplaceholder.typicode.com/posts`);
    }
}
