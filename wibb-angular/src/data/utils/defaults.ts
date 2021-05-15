import { Beer } from '../types/beer';
import { Meta } from '../types/meta';
import { Offer } from '../types/offer';
import { Store } from '../types/store';

const defaultMeta = new Meta('');
export const defaultBeer = new Beer('', '', defaultMeta);
export const defaultStore = new Store('', '', defaultMeta);
export const defaultOffer = new Offer(defaultStore, defaultBeer, 0, null, null);
