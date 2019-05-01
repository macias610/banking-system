import {Component, OnInit} from '@angular/core';
import {AccountService} from '../account.service';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';
import {AccountListItem} from '../../../../models/account/accountListItem';
import {NotificationService} from '../../../../shared/services/notification.service';

@Component({
    selector: 'app-account',
    templateUrl: './account.component.html',
    styleUrls: ['./account.component.scss']
})
export class AccountComponent implements OnInit {

    searchString: string;
    accounts$: Observable<AccountListItem[]>;

    constructor(
        private service: AccountService,
        private notiService: NotificationService,
        private http: HttpClient
    ) { }

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

    changeAccountStatus(account: AccountListItem) {
        this.service.changeAccountStatus(account.id).subscribe(
            (data) => {
                this.notiService.showNotification(data.notification || '', true);
                account.is_blocked = !account.is_blocked;

            },
            (error) => {
                const errorData = error.error;
                this.notiService.showNotification(errorData.notification || '', false);
            }
        );
    }

}
