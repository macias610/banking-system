import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';
import {TransferSendDao} from '../../models/transfer/TransferSendDao';
import {ResponseData} from '../../models/responseData';
import {DirectDebitAgreement} from '../../models/transfer/DirectDebitAgreement';

@Injectable({
  providedIn: 'root'
})
export class TransfersService {

  constructor(private http: HttpClient) {
  }

  getTransfers(id: String): Observable<ResponseData> {
    return this.http.get<ResponseData>(`${environment.api_url}/transactions/client/` + id);
  }

  getTransfer(id: String): Observable<ResponseData> {
    return this.http.get<ResponseData>(`${environment.api_url}/transactions/` + id);
  }

  sendTransfer(transfer: TransferSendDao): Observable<ResponseData> {
    return this.http
      .post<ResponseData>(`${environment.api_url}/transactions`, transfer);
  }

  applyDirectDebitAgreement(directDebitAgreement: DirectDebitAgreement): Observable<ResponseData> {
    return this.http.post<ResponseData>(`${environment.api_url}/directDebits`, directDebitAgreement);
  }

  getProviders(): Observable<ResponseData> {
    return this.http.get<ResponseData>(`${environment.api_url}/directDebits/providers`);
  }
}
