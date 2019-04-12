import { Pipe, PipeTransform } from '@angular/core';

const NUMBER_LENGTH = 26;

@Pipe({
    name: 'accountNumber'
})
export class AccountNumberPipe implements PipeTransform {

    transform(item: string): any {
        if (!item || item.length !== NUMBER_LENGTH) {
            return item;
        }

        return item.replace(/(\d{2})(\d{4})(\d{4})(\d{4})(\d{4})(\d{4})(\d{4})/, '$1 $2 $3 $4 $5 $6 $7');

    }

}
