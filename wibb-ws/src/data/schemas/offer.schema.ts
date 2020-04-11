import { Schema, model } from 'mongoose';

const OfferSchema = new Schema({
    beer: Object,
    store: Object,
    price: Number,
    startDate: Date,
    endDate: Date
});

export const OfferModel = model('Offer', OfferSchema);