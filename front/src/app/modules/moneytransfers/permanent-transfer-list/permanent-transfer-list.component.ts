import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {TransfersService} from '../transfers.service';
import {ClientsService} from '../../ror/clients/clients.service';
import {debounceTime, distinctUntilChanged, map} from 'rxjs/operators';
import {ResponseData} from '../../../models/responseData';
import {PermanentTransfer} from '../../../models/transfer/permanentTransfer';
import {NotificationService} from '../../../shared/services/notification.service';
import {ThinClient} from '../../../models/client/thinClient';

@Component({
  selector: 'app-permanent-transfer-list',
  templateUrl: './permanent-transfer-list.component.html',
  styleUrls: ['./permanent-transfer-list.component.scss']
})
export class PermanentTransferListComponent implements OnInit {

  searchString: string;
  transfers: Observable<PermanentTransfer[]>;
  clients: ThinClient[] = [];
  selectedClientId: string;

  constructor(private http: HttpClient
              , private service: TransfersService
              , private clientService: ClientsService
              , private notiService: NotificationService) { }

  ngOnInit() {
    this.searchString = '';
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

  selectClient(clientId: string) {
    this.selectedClientId = clientId;
    this.transfers = this.service.getPermanentTransfersForClient(clientId).pipe(
      map(item => item.data)
    );
  }

  cancelPermanentTransaction(transfer: PermanentTransfer) {
    this.service.cancelPermanentTransfer(transfer.id).subscribe(
      (data: ResponseData) => {
        this.notiService.showNotification(data.notification || '', true);
        this.selectClient(this.selectedClientId);
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

  permanentTransfersFilter(item: PermanentTransfer, search: string): boolean {
    const itemString = (item.title).toLocaleLowerCase();
    return itemString.indexOf(search) >= 0;
  }

}
