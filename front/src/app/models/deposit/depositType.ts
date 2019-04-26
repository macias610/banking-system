export class DepositType {
  constructor(public id: number,
    public name: string,
    public maxAmount: number,
    public minAmount: number,
    public interestRate: number,
    public daysPeriod: number) {}
}
