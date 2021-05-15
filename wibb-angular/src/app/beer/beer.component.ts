import { Component, Input } from '@angular/core';
import { Beer } from 'src/data/types/beer';
import { defaultBeer } from 'src/data/utils/defaults';

@Component({
  selector: 'app-beer',
  templateUrl: './beer.component.html',
  styleUrls: ['./beer.component.less'],
})
export class BeerComponent {
  @Input() beer: Beer;

  constructor() {
    this.beer = defaultBeer;
  }
}
