import { Meta } from "./meta.model";

export class Store {
    constructor(
        public id: string,
        public name: string,
        public icon: string,
        public meta: Meta) {
    }
}