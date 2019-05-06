export interface PermanentTransferAddRequest {
  senderAccNumber: number;
  receiverAccNumber: number;
  title: string;
  value: number;
  transactionDate: Date;
  dateFrom: Date;
  dateTo: Date;
  interval: number;
}
