import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {Client} from '../../../models/client/client';
import {HttpClient} from '@angular/common/http';
import {TransfersService} from '../transfers.service';
import {ClientsService} from '../../ror/clients/clients.service';
import {map} from 'rxjs/operators';
import {ResponseData} from '../../../models/responseData';
import {PermanentTransfer} from '../../../models/transfer/permanentTransfer';
import {NotificationService} from '../../../shared/services/notification.service';

@Component({
  selector: 'app-permanent-transfer-list',
  templateUrl: './permanent-transfer-list.component.html',
  styleUrls: ['./permanent-transfer-list.component.scss']
})
export class PermanentTransferListComponent implements OnInit {

  searchString: string;
  transfers: Observable<PermanentTransfer[]>;
  clients: Observable<Client[]>;
  selectedClientId: string;

  constructor(private http: HttpClient
              , private service: TransfersService
              , private clientService: ClientsService
              , private notiService: NotificationService) { }

  ngOnInit() {
    this.searchString = '';
    this.clients = this.clientService.getClients().pipe(
      map(item => item.data)
    );
  }

  onChange(clientId: string) {
    this.selectedClientId = clientId;
    this.transfers = this.service.getPermanentTransfersForClient(clientId).pipe(
      map(item => item.data)
    );
  }

  cancelPermanentTransaction(transfer: PermanentTransfer) {
    this.service.cancelPermanentTransfer(transfer.id).subscribe(
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

  permanentTransfersFilter(item: PermanentTransfer, search: string): boolean {
    const itemString = (item.title).toLocaleLowerCase();
    return itemString.indexOf(search) >= 0;
  }

}
