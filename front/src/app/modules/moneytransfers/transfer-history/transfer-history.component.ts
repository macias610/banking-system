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
        this.transfers = this.service.getTransfers(this.accountId).pipe(
            map(item => item.data),
            catchError(err => of([]))
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
