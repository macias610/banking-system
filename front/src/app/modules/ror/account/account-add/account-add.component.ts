import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';
import { Notification } from '../../../../models/notification';
import { ResponseData } from '../../../../models/responseData';
import { AccountService } from '../account.service';
import { ClientsService } from '../../clients/clients.service';
import { ThinClient } from '../../../../models/client/thinClient';
import { Observable } from 'rxjs';
import { debounceTime, distinctUntilChanged, map } from 'rxjs/operators';
import { Router } from '@angular/router';

@Component({
    selector: 'app-account-add',
    templateUrl: './account-add.component.html',
    styleUrls: ['./account-add.component.scss']
})
export class AccountAddComponent implements OnInit {

    clients: ThinClient[] = [];
    formInSave = false;
    createAccountForm: FormGroup;
    notification: Notification = new Notification();
    notificationTimer;

    constructor(private router: Router, private fb: FormBuilder, private service: AccountService, private clientService: ClientsService) { }

    ngOnInit() {
        this.clientService.getAllClients().subscribe(
            (data: ResponseData) => {
                this.clients = data.data;
                this.clients.forEach(item =>
                    item.search_str = `${item.first_name} ${item.surname} ${item.pesel}`
                );
            }
        );
        this.createAccountForm = this.fb.group({
            'client': ['', [Validators.required]],
            'client_id': ['', [Validators.required]],
            'currency': ['', [Validators.required]]
        });
    }

    addNotification(error: boolean, msg: string) {
        this.notification.message = msg;
        this.notification.error = error;
        if (this.notificationTimer) {
            clearTimeout(this.notificationTimer);
        }

        this.notificationTimer = setTimeout(() => {
            this.notification = new Notification();
        }, 3000);
    }

    searchClient = (text$: Observable<string>) =>
        text$.pipe(
            debounceTime(200),
            distinctUntilChanged(),
            map(term => term.length < 2 ? []
                : this.clients.filter(client => client.search_str.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10))
        )

    searchClientFormat(value: ThinClient): string {
        if (value) {
            return `${value.first_name} ${value.surname} (${value.pesel})`;
        } else {
            return '';
        }
    }

    selectClient(client: ThinClient) {
        this.createAccountForm.setValue({
            'client': this.searchClientFormat(client),
            'client_id': client.id,
            'currency': this.createAccountForm.controls['currency'].value
        });
    }


    createAccount() {
        const formValue = this.createAccountForm.value;
        this.formInSave = true;

        this.service.createAccount(formValue).subscribe(
            (data: ResponseData) => {
                this.formInSave = true;
                this.createAccountForm.reset();
                this.router.navigateByUrl('/accounts');
            },
            (error) => {
                const errorData: ResponseData = error.error;
                this.formInSave = true;
                this.addNotification(true, errorData ? errorData.notification : '');
            }
        );

    }

}
