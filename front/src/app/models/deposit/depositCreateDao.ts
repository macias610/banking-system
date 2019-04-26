export class DepositCreateDao {
  constructor(public id: number,
              public accountId: number,
              public depositTypeId: number,
              public startDate: string,
              public endDate: string,
              public amount: number,
              public isActive: boolean) {
  }
}
