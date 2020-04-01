import { Offer } from './offer.model';

export class Report {
    constructor(
        public id: string,
        public type: string,
        public info: string,
        public offer: Offer,
        public solved: boolean
    ){
    
    }
}