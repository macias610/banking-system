export class Credit {
    constructor(public id: number,
                public accountId: number,
                public creditTypeId: number,
                public creditBalanceId: number,
                public value: number,
                public isActive: boolean,
                public created_at: string,
                public expiration_at: string) {

    }
}
