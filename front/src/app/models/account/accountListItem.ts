interface Owner {
    id: string;
    surname: string;
    pesel: string;
    first_name: string;
}

export interface AccountListItem {
    id: string;
    first_name: string;
    surname: string;
    pesel: string;
    number_banking_account: string;
    is_active: boolean;
    is_blocked: boolean;
    currency: string;
    available_amount: number;
    owner: Owner;
}
