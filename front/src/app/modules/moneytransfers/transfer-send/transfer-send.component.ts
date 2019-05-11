import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TransfersService } from '../transfers.service';
import { ResponseData } from '../../../models/responseData';
import { ClientsService } from '../../ror/clients/clients.service';
import { Observable } from 'rxjs';
import { Client } from '../../../models/client/client';
import { map } from 'rxjs/operators';
import { AccountService } from '../../ror/account/account.service';
import { NotificationService } from '../../../shared/services/notification.service';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'app-transfer-send',
    templateUrl: './transfer-send.component.html',
    styleUrls: ['./transfer-send.component.scss']
})
export class TransferSendComponent implements OnInit {

    accountNumber: string;
    accountId: string;
    formInSave = false;
    sendTransferForm: FormGroup;
    clients: Observable<Client[]>;
    account: Observable<Account>;

    constructor(
        private notiService: NotificationService,
        private fb: FormBuilder,
        private service: TransfersService,
        private route: ActivatedRoute,
        private clientService: ClientsService,
        private accountService: AccountService) { }

    ngOnInit() {
        this.accountId = this.route.snapshot.paramMap.get('id');
        this.sendTransferForm = this.fb.group({
            'senderAccNumber': ['', [Validators.required]],
            'value': ['', [Validators.required]],
            'title': ['', [Validators.required]],
            'receiverAccNumber': ['', [Validators.required]],
            'transactionDate': ['', [Validators.required]],
        });

        this.account = this.accountService.getAccount(this.accountId).pipe(
            map(item => {
                this.accountNumber = item.data.number_banking_account;
                this.sendTransferForm.patchValue({ 'senderAccNumber': this.accountNumber });
                return item.data;
            })
        );
    }

    sendTransfer() {
        const formValue = this.sendTransferForm.value;
        this.formInSave = true;

        this.service.sendTransfer(formValue).subscribe(
            (data: ResponseData) => {
                this.formInSave = false;
                this.sendTransferForm.reset();
                this.sendTransferForm.patchValue({ 'senderAccNumber': this.accountNumber });
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
