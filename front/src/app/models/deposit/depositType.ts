export class DepositType {
  constructor(public id: number,
    public name: string,
    public minAmount: number,
    public maxAmount: number,
    public interestRate: number,
    public daysPeriod: number,
    public capitalizationType: string) {}
}
