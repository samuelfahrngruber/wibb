export class WibbError {
    constructor(
        public id: string,
        public occurrenceDescription: string,
        public message: string,
        public stackTrace: string
    ) {}
}