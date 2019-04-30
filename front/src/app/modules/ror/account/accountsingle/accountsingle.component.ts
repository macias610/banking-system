import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AccountService } from '../account.service';
import { CardService } from '../card.service';
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
    cards$: Observable<any>;

    constructor(
        private accountService: AccountService,
        private cardService: CardService,
        private route: ActivatedRoute
    ) { }

    ngOnInit() {
        this.accountId = this.route.snapshot.paramMap.get('id');
        this.account$ = this.accountService.getAccount(this.accountId).pipe(
            map(item => item.data)
        );
        this.refreshCardList();
    }

    refreshCardList() {
        this.cards$ = this.cardService.getCardsForAccount(this.accountId).pipe(
            map(item => item.data)
        );
    }

    addCard() {
        this.cardService.addNewCardForAccount({ accountId: this.accountId }).subscribe(
            (data) => {
                this.refreshCardList();
                // this.formInSave = false;

                // this.addNotification(false, data.notification || '');
            },
            (error) => {
                // const errorData = error.error;
                // this.formInSave = false;
                // this.addNotification(true, errorData ? errorData.notification : '');
            }
        );
    }

}
