import { Component, OnInit } from '@angular/core';
import { AccountService, Account } from '../account.service';
import { Observable } from 'rxjs';
import { catchError, map, switchMap } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';

@Component({
    selector: 'app-account',
    templateUrl: './account.component.html',
    styleUrls: ['./account.component.scss']
})
export class AccountComponent implements OnInit {

    accounts$: Observable<Account[]>;

    constructor(private service: AccountService, private http: HttpClient) { }

    ngOnInit() {
        this.accounts$ = this.service.getAccounts();
    }

}
