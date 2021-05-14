import { Beer } from './beer';
import { Store } from './store';

export interface RawOffer {
  store: Store;
  beer: Beer;
  price: number;
  startDate: string | null;
  endDate: string | null;
}
