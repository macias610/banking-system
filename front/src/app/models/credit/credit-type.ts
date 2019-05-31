export class CreditType {
  constructor(public id:number,
    public name: string,
    public info: string,
    public min_value: number,
    public max_value: number,
    public loan_period: string,
    public interest_rate: number) {}
}
