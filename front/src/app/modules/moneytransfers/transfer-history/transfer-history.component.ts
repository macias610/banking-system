import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {TransfersService} from '../transfers.service';
import {Transfer} from '../../../models/transfer/transfer';
import {map} from 'rxjs/operators';
import {Client} from '../../../models/client/client';
import {ClientsService} from '../../ror/clients/clients.service';

@Component({
  selector: 'app-transfer-history',
  templateUrl: './transfer-history.component.html',
  styleUrls: ['./transfer-history.component.scss']
})
export class TransferHistoryComponent implements OnInit {

  searchString: string;
  transfers: Observable<Transfer[]>;
  clients: Observable<Client[]>;

  constructor(private service: TransfersService
              , private clientService: ClientsService) { }

  ngOnInit() {
    this.searchString = '';
    // this.transfers = this.service.getTransfers('1').pipe(
    //   map(item => item.data)
    // );

    this.clients = this.clientService.getClients().pipe(
      map(item => item.data)
    );
  }

  onChange(clientId: string) {
    this.transfers = this.service.getTransfers(clientId).pipe(
      map(item => item.data)
    );
  }

  searchInTable(searchItem: string) {
    searchItem = searchItem.replace(/ /g, '');
    searchItem = searchItem.toLocaleLowerCase();
    this.searchString = searchItem;
  }

  transfersFilter(item: Transfer, search: string): boolean {
    const itemString = (item.title).toLocaleLowerCase();
    return itemString.indexOf(search) >= 0;
  }

}
