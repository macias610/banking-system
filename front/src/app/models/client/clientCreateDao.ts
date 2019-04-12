export interface ClientCreateDao {
    first_name: string;
    surname: string;
    pesel: number;
    street: string;
    house_number: string;
    apatment_number: string;
    city: string;
    zip: string;
    country: string;
    contacts: { type: string, value: string }[];
    documents: { type: string, value: string }[];

}

