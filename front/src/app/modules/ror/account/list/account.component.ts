import { Component, OnInit } from '@angular/core';
import { AccountService } from '../account.service';
import { Observable } from 'rxjs';
import { catchError, map, switchMap, filter, flatMap, tap } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { Account } from '../../../../models/account';

@Component({
    selector: 'app-account',
    templateUrl: './account.component.html',
    styleUrls: ['./account.component.scss']
})
export class AccountComponent implements OnInit {

    searchString: string;
    accounts$: Observable<Account[]>;

    constructor(private service: AccountService, private http: HttpClient) { }

    ngOnInit() {
        this.searchString = '';
        this.accounts$ = this.service.getAccounts();
    }

    searchInTable(searchItem: string) {
        searchItem = searchItem.replace(/ /g, '');
        searchItem = searchItem.toLocaleLowerCase();
        this.searchString = searchItem;
    }

    accountFilter(item: Account, search: string): boolean {
        const itemString = (item.number + '' + item.owner.info.first_name + '' + item.owner.info.surname).toLocaleLowerCase();
        return itemString.indexOf(search) >= 0;
    }

}
