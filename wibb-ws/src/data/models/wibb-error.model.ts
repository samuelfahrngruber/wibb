export class WibbError {
    constructor(
        public occurrenceDescription: string,
        public message: string,
        public stackTrace: string) {}
}