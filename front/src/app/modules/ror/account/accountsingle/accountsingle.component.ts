import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AccountService } from '../account.service';
import { Observable } from 'rxjs';
import { AccountListItem } from '../../../../models/account/accountListItem';
import { map } from 'rxjs/operators';

@Component({
    selector: 'app-accountsingle',
    templateUrl: './accountsingle.component.html',
    styleUrls: ['./accountsingle.component.scss']
})
export class AccountsingleComponent implements OnInit {

    accountId: string;
    account$: Observable<AccountListItem>;

    constructor(private accountService: AccountService, private route: ActivatedRoute) { }

    ngOnInit() {
        this.accountId = this.route.snapshot.paramMap.get('id');
        this.account$ = this.accountService.getAccount(this.accountId).pipe(
            map(item => item.data)
        );
    }

}
