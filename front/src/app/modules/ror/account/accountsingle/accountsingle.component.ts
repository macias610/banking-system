import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AccountService } from '../account.service';
import { CardService } from '../card.service';
import { Observable } from 'rxjs';
import { AccountListItem } from '../../../../models/account/accountListItem';
import { map, catchError, debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { Card } from '../../../../models/card';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NotificationService } from '../../../../shared/services/notification.service';
import { ThinClient } from '../../../../models/client/thinClient';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ResponseData } from '../../../../models/responseData';
import { ClientsService } from '../../clients/clients.service';

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

    clients: ThinClient[] = [];
    formInSave = false;
    addAgentForm: FormGroup;

    constructor(
        private clientService: ClientsService,
        private accountService: AccountService,
        private cardService: CardService,
        private modalService: NgbModal,
        private notiService: NotificationService,
        private fb: FormBuilder,
        private route: ActivatedRoute
    ) { }

    ngOnInit() {
        this.accountId = this.route.snapshot.paramMap.get('id');
        this.getAccountData();
        this.refreshCardList();

        this.clientService.getAllClients().subscribe(
            (data: ResponseData) => {
                this.clients = data.data;
                this.clients.forEach(item =>
                    item.search_str = `${item.first_name} ${item.surname} ${item.pesel}`
                );
            }
        );

        this.addAgentForm = this.fb.group({
            'client': ['', [Validators.required]],
            'client_id': ['', [Validators.required]]
        });
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

    addAgent() {
        const formValue = this.addAgentForm.value;
        this.formInSave = true;
        formValue.account_id = this.accountId;

        this.accountService.addAgent(formValue).subscribe(
            (data: ResponseData) => {
                this.formInSave = false;
                this.addAgentForm.reset();
                this.getAccountData();
                this.notiService.showNotification(data.notification || '', true);
            },
            (error) => {
                const errorData: ResponseData = error.error;
                this.formInSave = false;
                this.notiService.showNotification(errorData.notification || '', false);
            }
        );

    }

    removeAgent(account) {
        this.accountService.removeAgent(account.id).subscribe(
            (data: ResponseData) => {
                this.formInSave = false;
                this.notiService.showNotification(data.notification || '', true);
                delete account.agent;
            },
            (error) => {
                const errorData: ResponseData = error.error;
                this.formInSave = false;
                this.notiService.showNotification(errorData.notification || '', false);
            }
        );

    }

    searchClient = (text$: Observable<string>) =>
        text$.pipe(
            debounceTime(200),
            distinctUntilChanged(),
            map(term => term.length < 2 ? []
                : this.clients.filter(client => client.search_str.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10))
        )

    searchClientFormat(value: ThinClient): string {
        if (value) {
            return `${value.first_name} ${value.surname} (${value.pesel})`;
        } else {
            return '';
        }
    }

    selectClient(client: ThinClient) {
        this.addAgentForm.setValue({
            'client': this.searchClientFormat(client),
            'client_id': client.id
        });
    }

}
