import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Observable} from 'rxjs';
import {TransfersService} from '../transfers.service';
import {ClientsService} from '../../ror/clients/clients.service';
import {AccountService} from '../../ror/account/account.service';
import {debounceTime, distinctUntilChanged, map} from 'rxjs/operators';
import {ResponseData} from '../../../models/responseData';
import {NotificationService} from '../../../shared/services/notification.service';
import {ThinClient} from '../../../models/client/thinClient';

@Component({
  selector: 'app-permanent-transfer-add',
  templateUrl: './permanent-transfer-add.component.html',
  styleUrls: ['./permanent-transfer-add.component.scss']
})
export class PermanentTransferAddComponent implements OnInit {

  formInSave = false;
  sendTransferForm: FormGroup;
  clients: ThinClient[] = [];
  accounts: Observable<Account[]>;

  constructor(private fb: FormBuilder
              , private service: TransfersService
              , private clientService: ClientsService
              , private accountService: AccountService
              , private notiService: NotificationService) {
  }

  ngOnInit() {
    this.sendTransferForm = this.fb.group({
      'senderAccNumber': ['', [Validators.required]],
      'value': ['', [Validators.required]],
      'title': ['', [Validators.required]],
      'receiverAccNumber': ['', [Validators.required]],
      'dateFrom': ['', [Validators.required]],
      'dateTo': ['', [Validators.required]],
      'interval': ['', [Validators.required]],
    });
    this.clientService.getAllClients().subscribe(
      (data: ResponseData) => {
        this.clients = data.data;
        this.clients.forEach(item =>
          item.search_str = `${item.first_name} ${item.surname} ${item.pesel}`
        );
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
    this.accounts = this.accountService.getAccountsForClient(client.id).pipe(
      map(item => item.data)
    );
  }

  sendTransfer() {
    const formValue = this.sendTransferForm.value;
    this.formInSave = true;

    this.service.setPermanentTransfer(formValue).subscribe(
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
