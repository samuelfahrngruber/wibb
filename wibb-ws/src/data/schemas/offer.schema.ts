import { Schema, model } from 'mongoose';

const OfferSchema = new Schema({
    beer: Object,
    store: Object,
    price: Number,
    startDate: Date,
    endDate: Date,
    expires: Date,
    type: String
});

export const OfferModel = model('Offer', OfferSchema);