import { Schema, model } from 'mongoose';

const StoreSchema = new Schema({
    name: String,
    icon: String,
    meta: Object
});

export const StoreModel = model('Store', StoreSchema);