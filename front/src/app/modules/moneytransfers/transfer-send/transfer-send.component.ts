import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {TransfersService} from '../transfers.service';
import {Notification} from '../../../models/notification';

@Component({
  selector: 'app-transfer-send',
  templateUrl: './transfer-send.component.html',
  styleUrls: ['./transfer-send.component.scss']
})
export class TransferSendComponent implements OnInit {

  formInSave = false;
  sendTransferForm: FormGroup;
  notification: Notification = new Notification();
  notificationTimer;

  constructor(private fb: FormBuilder, private service: TransfersService) {
  }

  ngOnInit() {
    this.sendTransferForm = this.fb.group({
      'senderAccNumber': ['', [Validators.required]],
      'value': ['', [Validators.required]],
      'title': ['', [Validators.required]],
      'receiverAccNumber': ['', [Validators.required]],
      'transactionDate': ['', [Validators.required]],
    });
  }

  sendTransfer() {
    const formValue = this.sendTransferForm.value;
    this.formInSave = true;

    this.service.sendTransfer(formValue).subscribe(
      (data) => {
        this.formInSave = false;
        this.sendTransferForm.reset();
        this.addNotification(false, data.notification || '');
      },
      (error) => {
        const errorData = error.error;
        this.formInSave = false;
        this.addNotification(true, errorData ? errorData.notification : '');
      }
    );
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
}
