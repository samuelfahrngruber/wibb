import { Beer } from "./beer.model";
import { Store } from "./store.model";

export class Offer {
    constructor(
        public beer: Beer,
        public store: Store,
        public price: number,
        public startDate: Date,
        public endDate: Date) {
    }
}