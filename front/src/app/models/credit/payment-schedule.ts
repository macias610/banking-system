export class PaymentSchedule {
  constructor(public id: number,
              public creditId: number,
              public payment_date: string,
              public payment_assets: number,
              public payment_interest: number,
              public active: boolean) {

              }
}
