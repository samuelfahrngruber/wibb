import mongoose from 'mongoose';
import BeerSchema from './beerschema';
import StoreSchema from './storeschema';

let Schema = mongoose.Schema;

let offerSchema = new Schema(
    {
        beer: Object,
        store: Object,
        price: Number,
        startDate: Date,
        endDate: Date
    },
    {
        versionKey: false
    });
  

module.exports = mongoose.model('Offer', offerSchema);