import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {Client} from '../../../models/client/client';
import {TransfersService} from '../transfers.service';
import {ClientsService} from '../../ror/clients/clients.service';
import {map} from 'rxjs/operators';
import {DirectDebitListItem} from '../../../models/transfer/directDebitListItem';
import {HttpClient} from '@angular/common/http';
import {ResponseData} from '../../../models/responseData';
import {NotificationService} from '../../../shared/services/notification.service';

@Component({
  selector: 'app-direct-debit-list',
  templateUrl: './direct-debit-list.component.html',
  styleUrls: ['./direct-debit-list.component.scss']
})
export class DirectDebitListComponent implements OnInit {

  searchString: string;
  directDebits: Observable<DirectDebitListItem[]>;
  clients: Observable<Client[]>;
  selectedClientId: string;

  constructor(private http: HttpClient
              , private service: TransfersService
              , private clientService: ClientsService
              , private notiService: NotificationService) {
  }

  ngOnInit() {
    this.searchString = '';
    this.clients = this.clientService.getClients().pipe(
      map(item => item.data)
    );
  }

  onChange(clientId: string) {
    this.selectedClientId = clientId;
    this.directDebits = this.service.getDirectDebitsForClient(clientId).pipe(
      map(item => item.data)
    );
  }

  cancelDirectDebit(directDebit: DirectDebitListItem) {
    this.service.cancelDirectDebit(directDebit.id).subscribe(
      (data: ResponseData) => {
        this.notiService.showNotification(data.notification || '', true);
        this.onChange(this.selectedClientId);
      },
      (error) => {
        const errorData: ResponseData = error.error;
        console.log(errorData);
        this.notiService.showNotification(errorData ? errorData.notification : '', false);
      }
    );
  }

  searchInTable(searchItem: string) {
    searchItem = searchItem.replace(/ /g, '');
    searchItem = searchItem.toLocaleLowerCase();
    this.searchString = searchItem;
  }

  directDebitsFilter(item: DirectDebitListItem, search: string): boolean {
    const itemString = (item.providerName).toLocaleLowerCase();
    return itemString.indexOf(search) >= 0;
  }
}
