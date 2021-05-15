import { Component } from '@angular/core';
import { Beer } from 'src/data/types/beer';
import { Offer } from 'src/data/types/offer';
import { Store } from 'src/data/types/store';
import { WibbApiService } from './wibb-api.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.less'],
})
export class AppComponent {
  title = 'wibb-angular';

  beers: Beer[] = [];
  stores: Store[] = [];
  offers: Offer[] = [];

  constructor(private wibbApiService: WibbApiService) {}

  async ngOnInit() {
    this.beers = await this.wibbApiService.getBeers();
    this.stores = await this.wibbApiService.getStores();
    this.offers = await this.wibbApiService.getOffers();

    console.log(this.offers);
  }

  addOffer(newOffer: Offer) {
    this.offers.push(newOffer);
  }
}
