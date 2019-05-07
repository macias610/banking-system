export interface TransferSendDao {
  senderAccNumber: number;
  receiverAccNumber: number;
  title: string;
  value: number;
  transactionDate: Date;
}

