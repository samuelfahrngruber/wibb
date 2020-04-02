var mongoose = require('mongoose');

var Schema = mongoose.Schema;

var storeSchema = new Schema(
    {
        name: String,
        icon: String,
        iconMeta: Object
    },
    {
        versionKey: false
    });
  

module.exports = mongoose.model('Store', storeSchema);