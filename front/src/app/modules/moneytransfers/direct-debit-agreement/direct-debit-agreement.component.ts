import {Component, OnInit} from '@angular/core';
import {map} from 'rxjs/operators';
import {Observable} from 'rxjs';
import {Client} from '../../../models/client/client';
import {ClientsService} from '../../ror/clients/clients.service';
import {AccountService} from '../../ror/account/account.service';
import {Provider} from '../../../models/account/provider';
import {TransfersService} from '../transfers.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Notification} from '../../../models/notification';
import {ResponseData} from '../../../models/responseData';

@Component({
  selector: 'app-direct-debit-agreement',
  templateUrl: './direct-debit-agreement.component.html',
  styleUrls: ['./direct-debit-agreement.component.scss']
})
export class DirectDebitAgreementComponent implements OnInit {

  formInSave = false;
  applyDirectDebitAgreementForm: FormGroup;
  notification: Notification = new Notification();
  notificationTimer;
  clients: Observable<Client[]>;
  accounts: Observable<Account[]>;
  providers: Observable<Provider[]>;

  constructor(private fb: FormBuilder, private service: TransfersService, private clientService: ClientsService, private accountService: AccountService) {
  }

  ngOnInit() {

    this.applyDirectDebitAgreementForm = this.fb.group({
      'clientAccNumber': ['', [Validators.required]],
      'providerAccNumber': ['', [Validators.required]],
    });

    this.clients = this.clientService.getClients().pipe(
      map(item => item.data)
    );

    this.providers = this.service.getProviders().pipe(
      map(item => item.data)
    );
  }

  onChange(clientId: string) {
    this.accounts = this.accountService.getAccountsForClient(clientId).pipe(
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
