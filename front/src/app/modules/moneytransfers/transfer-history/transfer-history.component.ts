import { Component, OnInit } from '@angular/core';
import { Observable, of } from 'rxjs';
import { TransfersService } from '../transfers.service';
import { Transfer } from '../../../models/transfer/transfer';
import { map, catchError } from 'rxjs/operators';
import { Client } from '../../../models/client/client';
import { ClientsService } from '../../ror/clients/clients.service';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'app-transfer-history',
    templateUrl: './transfer-history.component.html',
    styleUrls: ['./transfer-history.component.scss']
})
export class TransferHistoryComponent implements OnInit {

    dateStart: string = '';
    dateEnd: string = '';
    accountId: string;
    searchString: string;
    transfers: Observable<Transfer[]>;
    clients: Observable<Client[]>;

    constructor(private service: TransfersService,
        private clientService: ClientsService,
        private route: ActivatedRoute
    ) { }

    ngOnInit() {
        this.accountId = this.route.snapshot.paramMap.get('id');
        this.searchString = '';
        this.getTransferList();
    }

    getTransferList() {
        this.transfers = this.service.getTransfers(this.accountId, this.dateStart, this.dateEnd).pipe(
            map(item => item.data.sort((a, b) => (-1) * (a.id - b.id))),
            catchError(err => of([]))
        );
    }

    searchInTable(searchItem: string) {
        console.log(searchItem);
        searchItem = searchItem.replace(/ /g, '');
        searchItem = searchItem.toLocaleLowerCase();
        this.searchString = searchItem;
    }

    transfersFilter(item: Transfer, search: string): boolean {
        const itemString = (item.title).toLocaleLowerCase();
        return itemString.indexOf(search) >= 0;
    }

    searchDate() {
        this.getTransferList();
    }

    refresh() {
        this.getTransferList();
    }

}
