import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {Client} from '../../../models/client/client';
import {Notification} from '../../../models/notification';
import {HttpClient} from '@angular/common/http';
import {TransfersService} from '../transfers.service';
import {ClientsService} from '../../ror/clients/clients.service';
import {map} from 'rxjs/operators';
import {ResponseData} from '../../../models/responseData';
import {PermanentTransfer} from '../../../models/transfer/permanentTransfer';

@Component({
  selector: 'app-permanent-transfer-list',
  templateUrl: './permanent-transfer-list.component.html',
  styleUrls: ['./permanent-transfer-list.component.scss']
})
export class PermanentTransferListComponent implements OnInit {

  searchString: string;
  transfers: Observable<PermanentTransfer[]>;
  clients: Observable<Client[]>;
  notification: Notification = new Notification();
  notificationTimer;
  selectedClientId: string;

  constructor(private http: HttpClient, private service: TransfersService, private clientService: ClientsService) {
  }

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
        this.addNotification(false, data.notification || '');
        this.onChange(this.selectedClientId);
      },
      (error) => {
        const errorData: ResponseData = error.error;
        console.log(errorData);
        this.addNotification(true, errorData ? errorData.notification : '');
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
