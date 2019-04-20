import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';
import {Transfer} from '../../models/transfer/transfer';

@Injectable({
  providedIn: 'root'
})
export class TransfersService {

  constructor(private http: HttpClient) { }

  getTransfers(id: String): Observable<Transfer[]> {
    return this.http.get<Transfer[]>(`${environment.api_url}/transactions/clients/` + id);
  }

}