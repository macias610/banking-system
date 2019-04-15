import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, Validators } from '@angular/forms';
import { ClientsService } from '../clients.service';
import { Notification } from '../../../../models/notification';
import { ResponseData } from '../../../../models/responseData';

@Component({
    selector: 'app-clients-add',
    templateUrl: './clients-add.component.html',
    styleUrls: ['./clients-add.component.scss']
})
export class ClientsAddComponent implements OnInit {

    formInSave = false;
    createClientForm: FormGroup;
    notification: Notification = new Notification();
    notificationTimer;

    constructor(private fb: FormBuilder, private service: ClientsService) { }

    ngOnInit() {
        this.createClientForm = this.fb.group({
            'first_name': ['', [Validators.required, Validators.minLength(3)]],
            'surname': ['', [Validators.required, Validators.minLength(3)]],
            'pesel': ['', [Validators.required]],
            'street': ['', [Validators.required]],
            'house_number': ['', [Validators.required]],
            'apatment_number': ['', [Validators.required]],
            'city': ['', [Validators.required]],
            'zip': ['', [Validators.required]],
            'country': ['', [Validators.required]],
            'contacts': this.fb.array([]),
            'documents': this.fb.array([])
        });

        this.addContact();
        this.addDocument();
    }

    get contactForms() {
        return this.createClientForm.get('contacts') as FormArray
    }

    get documentsForms() {
        return this.createClientForm.get('documents') as FormArray
    }

    addContact() {

        const contact = this.fb.group({
            type: ['', [Validators.required]],
            value: ['', [Validators.required]]
        });

        this.contactForms.push(contact);

    }

    addDocument() {

        const document = this.fb.group({
            type: ['', [Validators.required]],
            value: ['', [Validators.required]]
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

    createClient() {
        const formValue = this.createClientForm.value;
        this.formInSave = true;

        this.service.createClient(formValue).subscribe(
            (data: ResponseData) => {
                this.formInSave = false;
                this.createClientForm.reset();
                this.cleanFormArray(this.contactForms);
                this.cleanFormArray(this.documentsForms);

                this.addContact();
                this.addDocument();

                this.addNotification(false, data.notification || '');
            },
            (error) => {
                const errorData: ResponseData = error.error;
                console.log(errorData);
                this.formInSave = false;
                this.addNotification(true, errorData ? errorData.notification : '');
            }
        );

    }

}
