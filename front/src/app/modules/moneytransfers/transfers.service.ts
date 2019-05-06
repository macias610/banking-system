import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';
import {TransferSendDao} from '../../models/transfer/transferSendDao';
import {ResponseData} from '../../models/responseData';
import {DirectDebitAgreement} from '../../models/transfer/directDebitAgreement';
import {PermanentTransferAddRequest} from '../../models/transfer/permanentTransferAdd';

@Injectable({
  providedIn: 'root'
})
export class TransfersService {

  constructor(private http: HttpClient) {
  }

  getTransfers(id: String): Observable<ResponseData> {
    return this.http.get<ResponseData>(`${environment.api_url}/transactions/client/` + id);
  }

  getDirectDebitsForClient(id: String): Observable<ResponseData> {
    return this.http.get<ResponseData>(`${environment.api_url}/directDebits/client/` + id);
  }

  getTransfer(id: String): Observable<ResponseData> {
    return this.http.get<ResponseData>(`${environment.api_url}/transactions/` + id);
  }

  sendTransfer(transfer: TransferSendDao): Observable<ResponseData> {
    return this.http
      .post<ResponseData>(`${environment.api_url}/transactions`, transfer);
  }

  setPermanentTransfer(transfer: PermanentTransferAddRequest): Observable<ResponseData> {
    return this.http
      .post<ResponseData>(`${environment.api_url}/permanentTransactions`, transfer);
  }

  applyDirectDebitAgreement(directDebitAgreement: DirectDebitAgreement): Observable<ResponseData> {
    return this.http.post<ResponseData>(`${environment.api_url}/directDebits`, directDebitAgreement);
  }

  cancelDirectDebit(id: String): Observable<ResponseData> {
    return this.http.put<ResponseData>(`${environment.api_url}/directDebits/` + id, null);
  }

  getProviders(): Observable<ResponseData> {
    return this.http.get<ResponseData>(`${environment.api_url}/directDebits/providers`);
  }

  getDirectDebitsForProvider(providerId: string): Observable<ResponseData> {
    return this.http.get<ResponseData>(`${environment.api_url}/directDebits/provider/` + providerId);
  }
}
