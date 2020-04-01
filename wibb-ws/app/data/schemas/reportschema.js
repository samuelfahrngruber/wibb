import mongoose from 'mongoose';
import offerSchema from './offerschema';

let Schema = mongoose.Schema;

let reportSchema = new Schema(
    {
        type: String,
        info: String,
        offer: Object,
        solved: Boolean
    },
    {
        versionKey: false
    });
  

module.exports = mongoose.model('Report', reportSchema);