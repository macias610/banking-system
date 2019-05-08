import { Component, OnInit } from '@angular/core';
import { map, debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { Client } from '../../../models/client/client';
import { ClientsService } from '../../ror/clients/clients.service';
import { AccountService } from '../../ror/account/account.service';
import { Provider } from '../../../models/account/provider';
import { TransfersService } from '../transfers.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ResponseData } from '../../../models/responseData';
import { NotificationService } from '../../../shared/services/notification.service';
import { ActivatedRoute } from '@angular/router';
import { ThinClient } from '../../../models/client/thinClient';

@Component({
    selector: 'app-direct-debit-agreement',
    templateUrl: './direct-debit-agreement.component.html',
    styleUrls: ['./direct-debit-agreement.component.scss']
})
export class DirectDebitAgreementComponent implements OnInit {

    formInSave = false;
    applyDirectDebitAgreementForm: FormGroup;
    clients: ThinClient[] = [];
    accounts: Observable<Account[]>;
    providers: Observable<Provider[]>;

    constructor(private fb: FormBuilder
        , private service: TransfersService
        , private clientService: ClientsService
        , private accountService: AccountService,
        private route: ActivatedRoute
        , private notiService: NotificationService) {
    }

    ngOnInit() {
        this.applyDirectDebitAgreementForm = this.fb.group({
            'client': ['', []],
            'client_id': ['', []],
            'clientAccNumber': ['', [Validators.required]],
            'providerAccNumber': ['', [Validators.required]],
        });

        this.clientService.getAllClients().subscribe(
            (data: ResponseData) => {
                this.clients = data.data;
                this.clients.forEach(item =>
                    item.search_str = `${item.first_name} ${item.surname} ${item.pesel}`
                );
            }
        );

        this.providers = this.service.getProviders().pipe(
            map(item => item.data)
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
        this.accounts = this.accountService.getAccountsForClient(client.id).pipe(
            map(item => item.data)
        );
    }

    applyDirectDebitAgreement() {
        const formValue = this.applyDirectDebitAgreementForm.value;
        this.formInSave = true;

        this.service.applyDirectDebitAgreement(formValue).subscribe(
            (data: ResponseData) => {
                this.formInSave = false;
                this.applyDirectDebitAgreementForm.reset();
                this.notiService.showNotification(data.notification || '', true);
            },
            (error) => {
                const errorData: ResponseData = error.error;
                this.formInSave = false;
                this.notiService.showNotification(errorData ? errorData.notification : '', false);
            }
        );
    }
}
