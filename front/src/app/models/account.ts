import { Client } from './client/client';

export interface Account {
    id: number;
    number: string;
    balance: number;
    owner: Client;
}
