export interface PermanentTransfer {
  id: string;
  senderAccNumber: number;
  receiverAccNumber: number;
  title: string;
  value: number;
  transactionDate: Date;
  dateFrom: Date;
  dateTo: Date;
  interval: number;
  isEnabled: boolean;
}
