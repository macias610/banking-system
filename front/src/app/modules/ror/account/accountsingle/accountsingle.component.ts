import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AccountService } from '../account.service';
import { CardService } from '../card.service';
import { Observable } from 'rxjs';
import { AccountListItem } from '../../../../models/account/accountListItem';
import { map } from 'rxjs/operators';
import { Card } from '../../../../models/card';
import { ToastrService } from 'ngx-toastr';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NotificationService } from '../../../../shared/services/notification.service';

const PIN_LENGTH = 4;

@Component({
    selector: 'app-accountsingle',
    templateUrl: './accountsingle.component.html',
    styleUrls: ['./accountsingle.component.scss']
})
export class AccountsingleComponent implements OnInit {

    selectedOperationType: string;
    selectedOperation: boolean;
    accountId: string;
    account$: Observable<AccountListItem>;
    cards$: Observable<Card[]>;

    constructor(
        private accountService: AccountService,
        private cardService: CardService,
        private modalService: NgbModal,
        private notiService: NotificationService,
        private route: ActivatedRoute
    ) { }

    ngOnInit() {
        this.accountId = this.route.snapshot.paramMap.get('id');
        this.getAccountData();
        this.refreshCardList();
    }

    getAccountData() {
        this.account$ = this.accountService.getAccount(this.accountId).pipe(
            map(item => item.data)
        );
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
                this.notiService.showNotification(data.notification || '', true);
            },
            (error) => {
                const errorData = error.error;
                this.notiService.showNotification(errorData.notification || '', false);
            }
        );
    }

    showPinModal(cardId, content) {
        this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then((result) => {
            if (result.length !== PIN_LENGTH) {
                this.notiService.showNotification('Wrong pin length', false);
            } else {
                this.changeCardPin(cardId, result);
            }
        }, (reason) => {

        });
    }

    changeCardPin(cardId: string, newPin: string) {
        const statusData = {
            id: cardId,
            type: 'PIN',
            value: newPin
        };

        this.cardService.changeCardStatus(statusData).subscribe(
            (data) => {
                this.notiService.showNotification(data.notification || '', true);
            },
            (error) => {
                const errorData = error.error;
                this.notiService.showNotification(errorData.notification || '', false);
            }
        );
    }

    changeCardStatus(card: Card) {
        card.status = !card.status;
        const statusData = {
            id: card.id,
            type: 'STATUS',
            value: card.status
        };

        this.cardService.changeCardStatus(statusData).subscribe(
            (data) => {
                this.notiService.showNotification(data.notification || '', true);
            },
            (error) => {
                const errorData = error.error;
                this.notiService.showNotification(errorData.notification || '', false);
            }
        );
    }

    openCashIn() {
        this.selectedOperation = true;
        this.selectedOperationType = 'CASH_IN';
    }

    openCashOut() {
        this.selectedOperation = true;
        this.selectedOperationType = 'CASH_OUT';
    }

    closeInput() {
        this.selectedOperation = false;
        this.selectedOperationType = null;
    }

    addAmount(value, account: AccountListItem) {
        if (value) {

            this.accountService.changeAmountOfAccount(this.selectedOperationType, value, this.accountId).subscribe(
                (data) => {
                    this.notiService.showNotification(data.notification || '', true);
                    if (this.selectedOperationType === 'CASH_OUT') {
                        account.available_amount -= parseFloat(value);
                    } else {
                        account.available_amount += parseFloat(value);
                    }
                    this.closeInput();
                },
                (error) => {
                    const errorData = error.error;
                    this.notiService.showNotification(errorData.notification || '', false);
                }
            );

        }
    }

}
