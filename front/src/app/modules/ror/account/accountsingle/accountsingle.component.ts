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

const PIN_LENGTH = 4;

@Component({
    selector: 'app-accountsingle',
    templateUrl: './accountsingle.component.html',
    styleUrls: ['./accountsingle.component.scss']
})
export class AccountsingleComponent implements OnInit {

    accountId: string;
    account$: Observable<AccountListItem>;
    cards$: Observable<Card[]>;

    constructor(
        private accountService: AccountService,
        private cardService: CardService,
        private toastr: ToastrService,
        private modalService: NgbModal,
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

    showNotification(message, isGood) {
        if (isGood) {
            this.toastr.info('<span class="now-ui-icons ui-1_bell-53"></span>' + message, '', {
                timeOut: 5000,
                enableHtml: true,
                closeButton: true,
                toastClass: 'alert alert-info alert-with-icon',
                positionClass: 'toast-top-right'
            });
        } else {
            this.toastr.error('<span class="now-ui-icons ui-1_bell-53"></span>' + message, '', {
                timeOut: 5000,
                enableHtml: true,
                closeButton: true,
                toastClass: 'alert alert-danger alert-with-icon',
                positionClass: 'toast-top-right'
            });
        }
    }

    showPinModal(cardId, content) {
        this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then((result) => {
            if (result.length !== PIN_LENGTH) {
                this.showNotification('Wrong pin length', false);
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
                this.showNotification(data.notification || '', true);
            },
            (error) => {
                const errorData = error.error;
                this.showNotification(errorData.notification || '', false);
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
                this.showNotification(data.notification || '', true);
            },
            (error) => {
                const errorData = error.error;
                this.showNotification(errorData.notification || '', false);
            }
        );
    }

}
