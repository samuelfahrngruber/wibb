import { Pipe, PipeTransform } from '@angular/core';
import { WibbApiService } from './wibb-api.service';

@Pipe({
  name: 'apiResourcePipe',
})
export class ApiResourcePipePipe implements PipeTransform {
  constructor(private wibbApiService: WibbApiService) {}

  transform(value: string): string {
    return `${this.wibbApiService.apiHost}${value}`;
  }
}
