var mongoose = require('mongoose');

var Schema = mongoose.Schema;

var beerSchema = new Schema(
    {
        name: String,
        icon: String,
    },
    {
        versionKey: false
    });
  

module.exports = mongoose.model('Beer', beerSchema);