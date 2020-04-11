import { Meta } from "./meta.model";

export class Beer {
    constructor(
        public name: string,
        public icon: string,
        public meta: Meta) {
    }
}