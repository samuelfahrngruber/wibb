import mongoose from 'mongoose';

let Schema = mongoose.Schema;

let requestSchema = new Schema(
    {
        text: String,
    },
    {
        versionKey: false
    });
  

module.exports = mongoose.model('Request', requestSchema);