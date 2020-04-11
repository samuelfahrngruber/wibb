import { Schema, model } from 'mongoose';

const RequestSchema = new Schema({
    text: String,
});

export const RequestModel = model('Request', RequestSchema);