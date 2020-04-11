import { Offer } from "./offer.model";

export class Report {
    constructor(
        public type: string,
        public info: string,
        public offer: Offer,
        public solved: boolean) {
    }
}