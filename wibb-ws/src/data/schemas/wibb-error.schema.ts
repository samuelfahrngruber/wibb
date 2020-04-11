import { Schema, model } from 'mongoose';

const WibbErrorSchema = new Schema({
    occurrenceDescription: String,
    message: String,
    stackTrace: String
});

export const WibbErrorModel = model('WibbError', WibbErrorSchema);