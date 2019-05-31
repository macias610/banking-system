import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CreditsService } from '../credits.service';
import { PaymentSchedule } from '../../../models/credit/payment-schedule';
import { ResponseData } from '../../../models/responseData';

@Component({
  selector: 'app-payment-schedules-list',
  templateUrl: './payment-schedules-list.component.html',
  styleUrls: ['./payment-schedules-list.component.scss']
})
export class PaymentSchedulesListComponent implements OnInit {

  paymentSchedules: PaymentSchedule[];
  creditId: number;
  status: boolean;

  constructor(private service: CreditsService, private route: ActivatedRoute) { }
  
  ngOnInit() {
    this.setCreditData();
    this.getPaymentSchedules();
  }

  setCreditData(): void {
    this.route.params.subscribe(params => {
      this.creditId = params['id'];
    })
  }
  
  getPaymentSchedules(): void {
    this.service.getPaymentSchedules(this.creditId).subscribe((data: ResponseData) => {
      this.paymentSchedules = data['data'];
      this.paymentSchedules.sort((a,b) => a.payment_date.localeCompare(b.payment_date));
      console.log(this.paymentSchedules);
      
    },
    (error) => {
      this.paymentSchedules = [];
    })
  }

  getPaymentStatus(paymentId: number): string {
    this.status = this.paymentSchedules.filter(payment => payment.id === paymentId)[0].active;
    console.log(status);
    
    if(this.status === true){
      return "Unpaid";
    } else {
      return "Paid";
    }
  }
  

}
