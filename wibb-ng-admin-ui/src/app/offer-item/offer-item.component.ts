import { Component, OnInit, Input } from '@angular/core';
import { Offer } from '../shared/offer.model';

@Component({
  selector: 'app-offer-item',
  templateUrl: './offer-item.component.html',
  styleUrls: ['./offer-item.component.css']
})
export class OfferItemComponent implements OnInit {
  @Input() offer: Offer;

  constructor() { }

  ngOnInit(): void {
  }

}
