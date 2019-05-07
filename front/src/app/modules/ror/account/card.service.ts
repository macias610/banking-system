import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../../environments/environment';
import {ResponseData} from '../../../models/responseData';


@Injectable({
    providedIn: 'root'
})
export class CardService {

    constructor(private http: HttpClient) {
    }

    addNewCardForAccount(cardData: any): Observable<ResponseData> {
        return this.http.post<ResponseData>(`${environment.api_url}/card/save`, cardData);
    }

    changeCardStatus(cardData: any): Observable<ResponseData> {
        return this.http.put<ResponseData>(`${environment.api_url}/card/edit`, cardData);
    }

    getCardsForAccount(accountId: String): Observable<ResponseData> {
        return this.http.get<ResponseData>(`${environment.api_url}/card/account/${accountId}`);
    }
}
