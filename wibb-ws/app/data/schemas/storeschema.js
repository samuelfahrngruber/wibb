var mongoose = require('mongoose');

var Schema = mongoose.Schema;

var storeSchema = new Schema(
    {
        _id: Object,
        name: String,
        icon: String,
    },
    {
        versionKey: false
    });
  

module.exports = mongoose.model('Store', storeSchema);