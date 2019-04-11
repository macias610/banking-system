import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { ClientsService } from '../clients.service';
import { HttpClient } from '@angular/common/http';
import { Client } from '../../../../models/client/client';

@Component({
    selector: 'app-clients-list',
    templateUrl: './clients-list.component.html',
    styleUrls: ['./clients-list.component.scss']
})
export class ClientsListComponent implements OnInit {

    searchString: string;
    clients$: Observable<Client[]>;

    constructor(private service: ClientsService, private http: HttpClient) { }

    ngOnInit() {
        this.searchString = '';
        this.clients$ = this.service.getClients();
    }

    searchInTable(searchItem: string) {
        searchItem = searchItem.replace(/ /g, '');
        searchItem = searchItem.toLocaleLowerCase();
        this.searchString = searchItem;
    }

    clientsFilter(item: Client, search: string): boolean {
        const itemString = (item.info.first_name + '' + item.info.surname + '' + item.info.pesel).toLocaleLowerCase();
        return itemString.indexOf(search) >= 0;
    }

}
