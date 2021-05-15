import { Component, Input } from '@angular/core';
import { Offer } from 'src/data/types/offer';
import { defaultOffer } from 'src/data/utils/defaults';

@Component({
  selector: 'app-offer',
  templateUrl: './offer.component.html',
  styleUrls: ['./offer.component.less'],
})
export class OfferComponent {
  @Input() offer: Offer;

  constructor() {
    this.offer = defaultOffer;
  }
}
