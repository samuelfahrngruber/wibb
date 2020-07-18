import { Beer } from "./beer.model";
import { Store } from "./store.model";
import { WibbOfferType } from "../../util/WibbErrorUtil";

export class Offer {
    constructor(
        public beer: Beer,
        public store: Store,
        public price: number,
        public startDate: Date,
        public endDate: Date,
        public type: WibbOfferType) {
    }
}