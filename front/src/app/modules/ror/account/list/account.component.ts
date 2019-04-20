import { Component, OnInit } from '@angular/core';
import { AccountService } from '../account.service';
import { Observable } from 'rxjs';
import { catchError, map, switchMap, filter, flatMap, tap, debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { Account } from '../../../../models/account';
import { AccountListItem } from '../../../../models/account/accountListItem';
@Component({
    selector: 'app-account',
    templateUrl: './account.component.html',
    styleUrls: ['./account.component.scss']
})
export class AccountComponent implements OnInit {

    searchString: string;
    accounts$: Observable<AccountListItem[]>;

    constructor(private service: AccountService, private http: HttpClient) { }

    ngOnInit() {
        this.searchString = '';
        this.accounts$ = this.service.getAccounts().pipe(
            map(item => item.data)
        );
    }

    searchInTable(searchItem: string) {
        searchItem = searchItem.replace(/ /g, '');
        searchItem = searchItem.toLocaleLowerCase();
        this.searchString = searchItem;
    }

    accountFilter(item: AccountListItem, search: string): boolean {
        const itemString = (item.number_banking_account + '' + item.first_name + '' + item.surname).toLocaleLowerCase();
        return itemString.indexOf(search) >= 0;
    }

}
