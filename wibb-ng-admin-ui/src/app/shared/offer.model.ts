import { Beer } from './beer.model';
import { Store } from './store.model';

export class Offer {
    constructor(
        public id: string,
        public beer: Beer,
        public store: Store,
        public startDate: Date,
        public endDate: Date,
        public price: number){

    }
}