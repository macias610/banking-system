import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'customFilter'
})
export class CustomFilterPipe implements PipeTransform {

    transform(items: any[], callback: (item: any, search: string) => boolean, searchString: string): any {
        if (!items || !callback) {
            return items;
        }

        return items.filter(item => callback(item, searchString));

    }

}
