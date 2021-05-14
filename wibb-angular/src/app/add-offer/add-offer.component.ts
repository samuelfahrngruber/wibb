import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Beer } from 'src/data/types/beer';
import { Offer } from 'src/data/types/offer';
import { Store } from 'src/data/types/store';
import { WibbApiService } from '../wibb-api.service';

@Component({
  selector: 'app-add-offer',
  templateUrl: './add-offer.component.html',
  styleUrls: ['./add-offer.component.less'],
})
export class AddOfferComponent implements OnInit {
  beers: Beer[];
  stores: Store[];

  selectedBeer: string = '';
  selectedStore: string = '';
  selectedPrice: string = '';
  selectedStartDate: string = '';
  selectedEndDate: string = '';

  @Output() offerAddedEvent = new EventEmitter<Offer>();

  private static byName(query: string) {
    return (item: { name: string }) => item.name === query;
  }

  constructor(private wibbApiService: WibbApiService) {
    this.beers = [];
    this.stores = [];
  }

  async ngOnInit() {
    this.beers = await this.wibbApiService.getBeers();
    this.stores = await this.wibbApiService.getStores();
  }

  async onSubmit() {
    const store = this.stores.find(
      AddOfferComponent.byName(this.selectedStore)
    );
    const beer = this.beers.find(AddOfferComponent.byName(this.selectedBeer));
    const price = Number(this.selectedPrice);
    const startDate = new Date(this.selectedStartDate);
    const endDate = new Date(this.selectedEndDate);

    if (store && beer && price) {
      const newOffer = new Offer(store, beer, price, startDate, endDate);
      const result = await this.wibbApiService.addOffer(newOffer);

      console.log(result);

      this.offerAddedEvent.emit(newOffer);
    } else {
      console.error('Failed to add offer.');
    }
  }
}
