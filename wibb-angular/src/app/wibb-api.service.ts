import { Injectable } from '@angular/core';
import { Offer } from 'src/data/types/offer';
import { PROD_API_HOST } from 'src/data/utils/constants';
import {
  parseBeers,
  parseOffers,
  parseStores,
} from 'src/data/utils/dataParser';

@Injectable({
  providedIn: 'root',
})
export class WibbApiService {
  readonly apiUrl: string;
  readonly apiHost: string;

  private options: RequestInit = {
    mode: 'cors',
  };

  constructor() {
    this.apiHost = PROD_API_HOST;
    this.apiUrl = `${this.apiHost}/api`;
  }

  async getBeers() {
    const res = await fetch(`${this.apiUrl}/beers`, this.options);
    const json = await res.json();
    return parseBeers(json);
  }

  async getStores() {
    const res = await fetch(`${this.apiUrl}/stores`, this.options);
    const json = await res.json();
    return parseStores(json);
  }

  async getOffers() {
    const res = await fetch(`${this.apiUrl}/offers`, this.options);
    const json = await res.json();
    return parseOffers(json);
  }

  async addOffer(offer: Offer) {
    const res = await fetch(`${this.apiUrl}/offers`, {
      ...this.options,
      headers: {
        ...this.options.headers,
        Accept: 'application/json',
        'Content-Type': 'application/json',
      },
      method: 'POST',
      body: JSON.stringify(offer),
    });
    const json = await res.json();
    return json;
  }
}
