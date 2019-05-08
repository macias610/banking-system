import { Component, OnInit } from '@angular/core';
import { Observable, of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { TransfersService } from '../transfers.service';
import { DirectDebitListItem } from '../../../models/transfer/directDebitListItem';
import { ResponseData } from '../../../models/responseData';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Provider } from '../../../models/account/provider';
import { NotificationService } from '../../../shared/services/notification.service';

@Component({
    selector: 'app-direct-debit-send',
    templateUrl: './direct-debit-send.component.html',
    styleUrls: ['./direct-debit-send.component.scss']
})
export class DirectDebitSendComponent implements OnInit {

    formInSave = false;
    sendTransferForm: FormGroup;
    providers: Provider[] = [];
    directDebits: Observable<DirectDebitListItem[]>;
    selectedValue: Provider;

    constructor(private service: TransfersService
        , private fb: FormBuilder
        , private http: HttpClient
        , private notiService: NotificationService) {
    }

    ngOnInit() {
        this.sendTransferForm = this.fb.group({
            'senderAccNumber': ['', [Validators.required]],
            'value': ['', [Validators.required]],
            'title': ['', [Validators.required]],
            'receiverAccNumber': ['', []],
            'transactionDate': ['', [Validators.required]],
        });

        this.service.getProviders().subscribe(
            (data: ResponseData) => {
                this.providers = data.data;
            },
            (error) => {

            }
        );
    }

    onChange(providerId) {
        if (providerId) {
            this.selectedValue = this.providers.find(item => parseInt(item.id, 10) === parseInt(providerId, 10));
            this.directDebits = this.service.getDirectDebitsForProvider(providerId).pipe(
                map(item => item.data),
                catchError(err => { this.showError(err); return of([]); })
            );
        } else {
            this.selectedValue = null;
        }
    }

    showError(error) {
        const errorData: ResponseData = error.error;
        this.formInSave = false;
        this.notiService.showNotification(errorData ? errorData.notification : '', false);
    }

    sendTransfer() {
        this.sendTransferForm.patchValue({
            'receiverAccNumber': this.selectedValue.accountNumber
        });
        const formValue = this.sendTransferForm.value;
        this.formInSave = true;

        this.service.sendTransfer(formValue).subscribe(
            (data: ResponseData) => {
                this.formInSave = false;
                this.sendTransferForm.reset();
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
