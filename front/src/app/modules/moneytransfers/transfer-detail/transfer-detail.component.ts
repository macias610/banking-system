import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute} from '@angular/router';
import {TransfersService} from '../transfers.service';
import {Transfer} from '../../../models/transfer/transfer';
import {ResponseData} from '../../../models/responseData';

@Component({
  selector: 'app-transfer-detail',
  templateUrl: './transfer-detail.component.html',
  styleUrls: ['./transfer-detail.component.scss']
})
export class TransferDetailComponent implements OnInit {

  transferId: string;
  transferForm: FormGroup;

  constructor(private fb: FormBuilder, private service: TransfersService, private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.transferId = this.route.snapshot.paramMap.get('id');
    this.transferForm = this.fb.group({
      'id': ['', [Validators.required]],
      'title': ['', [Validators.required]],
      'transactionDate': ['', [Validators.required]],
      'value': ['', [Validators.required]],
      'senderAccNumber': ['', [Validators.required]],
      'receiverAccNumber': ['', [Validators.required]],
    });

    this.service.getTransfer(this.transferId).subscribe(
      (d: ResponseData) => {
        this.fillForm(<Transfer>d.data);
      },
    );
  }


  fillForm(transfer: Transfer) {
    this.transferForm.setValue({
      'id': transfer.id,
      'title': transfer.title,
      'transactionDate': transfer.transactionDate,
      'value': transfer.value,
      'senderAccNumber': transfer.senderAccNumber,
      'receiverAccNumber': transfer.receiverAccNumber,
    });
  }

}
