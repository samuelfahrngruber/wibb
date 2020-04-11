import { Schema, model } from 'mongoose';

const BeerSchema = new Schema({
    name: String,
    icon: String,
    meta: Object
});

export const BeerModel = model('Beer', BeerSchema);