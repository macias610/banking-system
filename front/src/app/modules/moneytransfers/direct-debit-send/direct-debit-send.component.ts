import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {TransfersService} from '../transfers.service';
import {DirectDebitListItem} from '../../../models/transfer/directDebitListItem';
import {ResponseData} from '../../../models/responseData';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {HttpClient} from '@angular/common/http';
import {Provider} from '../../../models/account/provider';
import {NotificationService} from '../../../shared/services/notification.service';

@Component({
  selector: 'app-direct-debit-send',
  templateUrl: './direct-debit-send.component.html',
  styleUrls: ['./direct-debit-send.component.scss']
})
export class DirectDebitSendComponent implements OnInit {

  formInSave = false;
  sendTransferForm: FormGroup;
  providers: Observable<Account[]>;
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
    this.providers = this.service.getProviders().pipe(
      map(item => item.data)
    );
  }

  onChange() {
    this.directDebits = this.service.getDirectDebitsForProvider(this.selectedValue.id).pipe(
      map(item => item.data)
    );
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
        console.log(errorData);
        this.formInSave = false;
        this.notiService.showNotification(errorData ? errorData.notification : '', false);
      }
    );
  }
}
