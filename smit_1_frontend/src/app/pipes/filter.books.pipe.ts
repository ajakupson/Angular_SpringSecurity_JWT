import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filterBooks',
  standalone: true
})
export class FilterBooksPipe implements PipeTransform {

  transform(items: any[], searchText: string): any[] {
    if (!items || !searchText) {
      return items;
    }

    searchText = searchText.toLowerCase();

    return items.filter(item =>
      item.title.toLowerCase().includes(searchText) || item.author.toLowerCase().includes(searchText)
    );
  }
}
