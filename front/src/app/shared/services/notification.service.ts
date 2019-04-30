import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError, of } from 'rxjs';
import { catchError, tap, map } from 'rxjs/operators';
import { ToastrService } from 'ngx-toastr';

@Injectable({
    providedIn: 'root'
})
export class NotificationService {

    constructor(private toastr: ToastrService) {

    }

    showNotification(message, isGood) {
        if (isGood) {
            this.toastr.info('<span class="now-ui-icons ui-1_bell-53"></span>' + message, '', {
                timeOut: 5000,
                enableHtml: true,
                closeButton: true,
                toastClass: 'alert alert-info alert-with-icon',
                positionClass: 'toast-top-right'
            });
        } else {
            this.toastr.error('<span class="now-ui-icons ui-1_bell-53"></span>' + message, '', {
                timeOut: 5000,
                enableHtml: true,
                closeButton: true,
                toastClass: 'alert alert-danger alert-with-icon',
                positionClass: 'toast-top-right'
            });
        }
    }
}
