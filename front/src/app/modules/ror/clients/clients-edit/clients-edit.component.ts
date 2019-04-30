import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, Validators } from '@angular/forms';
import { ClientsService } from '../clients.service';
import { Notification } from '../../../../models/notification';
import { ActivatedRoute } from '@angular/router';
import { ClientCreateDao } from '../../../../models/client/clientCreateDao';
import { ResponseData } from '../../../../models/responseData';
import { AccountService } from '../../account/account.service';
import { Observable } from 'rxjs';
import { AccountListItem } from '../../../../models/account/accountListItem';
import { map } from 'rxjs/operators';

@Component({
    selector: 'app-clients-edit',
    templateUrl: './clients-edit.component.html',
    styleUrls: ['./clients-edit.component.scss']
})
export class ClientsEditComponent implements OnInit {

    accounts$: Observable<AccountListItem[]>;
    clientId: string;
    formInSave = false;
    editClientForm: FormGroup;
    notification: Notification = new Notification();
    notificationTimer;

    constructor(
        private fb: FormBuilder,
        private service: ClientsService,
        private accountService: AccountService,
        private route: ActivatedRoute
    ) { }

    ngOnInit() {
        this.clientId = this.route.snapshot.paramMap.get('id');
        this.editClientForm = this.fb.group({
            'first_name': ['', [Validators.required, Validators.minLength(3)]],
            'surname': ['', [Validators.required, Validators.minLength(3)]],
            'pesel': ['', [Validators.required]],
            'street': ['', [Validators.required]],
            'house_number': ['', [Validators.required]],
            'apartment_number': ['', [Validators.required]],
            'city': ['', [Validators.required]],
            'zip': ['', [Validators.required]],
            'country': ['', [Validators.required]],
            'contacts': this.fb.array([]),
            'documents': this.fb.array([])
        });

        this.service.getClient(this.clientId).subscribe(
            (d: ResponseData) => {
                this.fillForm(<ClientCreateDao>d.data);
                console.log(d);
                // this.statuses = data.data;
            },
            (error) => {
                // this.statuses = [];
            }
        );

        this.accounts$ = this.accountService.getAccountsForClient(this.clientId).pipe(
            map(item => item.data)
        );
    }

    get contactForms() {
        return this.editClientForm.get('contacts') as FormArray
    }

    get documentsForms() {
        return this.editClientForm.get('documents') as FormArray
    }

    fillForm(client: ClientCreateDao) {
        this.editClientForm.setValue({
            'first_name': client.first_name,
            'surname': client.surname,
            'pesel': client.pesel,
            'street': client.street,
            'house_number': client.house_number,
            'apartment_number': client.apartment_number,
            'city': client.city,
            'zip': client.zip,
            'country': client.country,
            'contacts': [],
            'documents': [],
        });

        client.contacts = client.contacts || [];
        client.documents = client.documents || [];

        client.contacts.forEach((item) => {
            this.addContact(item);
        });
        client.documents.forEach((item) => {
            this.addDocument(item);
        });
    }

    addContact(item) {

        item = item || {};

        const contact = this.fb.group({
            type: [item.type || '', [Validators.required]],
            value: [item.value || '', [Validators.required]]
        });

        this.contactForms.push(contact);

    }

    addDocument(item) {

        item = item || {};

        const document = this.fb.group({
            type: [item.type || '', [Validators.required]],
            value: [item.value || '', [Validators.required]]
        });

        this.documentsForms.push(document);

    }

    deleteContact(i) {
        this.contactForms.removeAt(i);
    }

    deleteDocument(i) {
        this.documentsForms.removeAt(i);
    }

    cleanFormArray(formArray: FormArray) {
        while (formArray.length !== 0) {
            formArray.removeAt(0);
        }
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

    editClient() {
        const formValue = this.editClientForm.value;
        this.formInSave = true;

        // edit client
        this.service.editClient(this.clientId, formValue).subscribe(
            (data) => {
                this.formInSave = false;

                this.addNotification(false, data.notification || '');
            },
            (error) => {
                const errorData = error.error;
                this.formInSave = false;
                this.addNotification(true, errorData ? errorData.notification : '');
            }
        );

    }

}
