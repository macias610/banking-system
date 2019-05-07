import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../../environments/environment';
import {ClientCreateDao} from '../../../models/client/clientCreateDao';
import {ResponseData} from '../../../models/responseData';

@Injectable({
    providedIn: 'root'
})
export class ClientsService {

    constructor(private http: HttpClient) {
    }

    getAllClients(): Observable<ResponseData> {
        return this.http.get<ResponseData>(`${environment.api_url}/client/all`);
    }

    getClients(): Observable<ResponseData> {
        return this.http.get<ResponseData>(`${environment.api_url}/client/clients`);
    }

    getClient(clientId: string): Observable<ResponseData> {
        return this.http.get<ResponseData>(`${environment.api_url}/client/${clientId}`);
    }

    createClient(client: ClientCreateDao): Observable<ResponseData> {
        return this.http
            .post<ResponseData>(`${environment.api_url}/client/save`, client);
    }

    editClient(id: String, client: ClientCreateDao): Observable<ResponseData> {
        return this.http
            .put<ResponseData>(`${environment.api_url}/client/edit/${id}`, client);
    }
}
