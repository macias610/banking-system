import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ClientsService} from '../clients.service';
import {ResponseData} from '../../../../models/responseData';
import {NotificationService} from '../../../../shared/services/notification.service';

@Component({
    selector: 'app-clients-add',
    templateUrl: './clients-add.component.html',
    styleUrls: ['./clients-add.component.scss']
})
export class ClientsAddComponent implements OnInit {

    formInSave = false;
    createClientForm: FormGroup;

    constructor(
        private fb: FormBuilder,
        private notiService: NotificationService,
        private service: ClientsService
    ) { }

    ngOnInit() {
        this.createClientForm = this.fb.group({
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

    createClient() {
        const formValue = this.createClientForm.value;
        this.formInSave = true;

        this.service.createClient(formValue).subscribe(
            (data: ResponseData) => {
                this.formInSave = true;
                this.createClientForm.reset();
                this.cleanFormArray(this.contactForms);
                this.cleanFormArray(this.documentsForms);

                this.addContact();
                this.addDocument();

                this.notiService.showNotification(data.notification || '', true);
            },
            (error) => {
                const errorData: ResponseData = error.error;
                this.formInSave = true;

                this.notiService.showNotification(errorData.notification || '', false);
            }
        );

    }

}
