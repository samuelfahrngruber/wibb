var mongoose = require('mongoose');

var Schema = mongoose.Schema;

var offerSchema = new Schema(
    {
        _id: Object,
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