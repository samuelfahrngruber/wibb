import { Beer } from '../types/beer';
import { Offer } from '../types/offer';
import { RawOffer } from '../types/rawOffer';
import { Store } from '../types/store';

function parseDate(date: string | null) {
  return typeof date == 'string' ? new Date(date) : date;
}

export function parseBeers(json: any) {
  return <Beer[]>json;
}

export function parseStores(json: any) {
  return <Store[]>json;
}

export function parseOffers(json: any) {
  const array = <RawOffer[]>json;

  return array.map((item) => {
    return new Offer(
      item.store,
      item.beer,
      item.price,
      parseDate(item.startDate),
      parseDate(item.endDate)
    );
  });
}
