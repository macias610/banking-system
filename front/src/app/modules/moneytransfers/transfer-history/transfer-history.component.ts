import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {TransfersService} from '../transfers.service';
import {Transfer} from '../../../models/transfer/transfer';
import {map} from 'rxjs/operators';

@Component({
  selector: 'app-transfer-history',
  templateUrl: './transfer-history.component.html',
  styleUrls: ['./transfer-history.component.scss']
})
export class TransferHistoryComponent implements OnInit {

  searchString: string;
  transfers: Observable<Transfer[]>;

  constructor(private service: TransfersService) { }

  ngOnInit() {
    this.searchString = '';
    this.transfers = this.service.getTransfers('1').pipe(
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
