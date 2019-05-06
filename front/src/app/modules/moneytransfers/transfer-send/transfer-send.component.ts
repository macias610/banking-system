import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {TransfersService} from '../transfers.service';
import {Notification} from '../../../models/notification';
import {ResponseData} from '../../../models/responseData';
import {ClientsService} from '../../ror/clients/clients.service';
import {Observable} from 'rxjs';
import {Client} from '../../../models/client/client';
import {map} from 'rxjs/operators';
import {AccountService} from '../../ror/account/account.service';

@Component({
  selector: 'app-transfer-send',
  templateUrl: './transfer-send.component.html',
  styleUrls: ['./transfer-send.component.scss']
})
export class TransferSendComponent implements OnInit {

  formInSave = false;
  sendTransferForm: FormGroup;
  notification: Notification = new Notification();
  notificationTimer;
  clients: Observable<Client[]>;
  accounts: Observable<Account[]>;

  constructor(private fb: FormBuilder, private service: TransfersService, private clientService: ClientsService, private accountService: AccountService) {
  }

  ngOnInit() {
    this.sendTransferForm = this.fb.group({
      'senderAccNumber': ['', [Validators.required]],
      'value': ['', [Validators.required]],
      'title': ['', [Validators.required]],
      'receiverAccNumber': ['', [Validators.required]],
      'transactionDate': ['', [Validators.required]],
    });

    this.clients = this.clientService.getClients().pipe(
      map(item => item.data)
    );
  }

  onChange(clientId: string) {
    this.accounts = this.accountService.getAccountsForClient(clientId).pipe(
      map(item => item.data)
    );
  }

  sendTransfer() {
    const formValue = this.sendTransferForm.value;
    this.formInSave = true;

    this.service.sendTransfer(formValue).subscribe(
      (data: ResponseData) => {
        this.formInSave = false;
        this.sendTransferForm.reset();
        this.addNotification(false, data.notification || '');
      },
      (error) => {
        const errorData: ResponseData = error.error;
        console.log(errorData);
        this.formInSave = false;
        this.addNotification(true, errorData ? errorData.notification : '');
      }
    );
  }

  addNotification(error: boolean, msg: string) {
    this.notification.message = msg;
    this.notification.error = error;
    if (this.notificationTimer) {
      clearTimeout(this.notificationTimer);
    }

    this.notificationTimer = setTimeout(() => {
      this.notification = new Notification();
    }, 3000);
  }
}