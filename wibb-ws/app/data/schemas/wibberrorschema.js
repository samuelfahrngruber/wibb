var mongoose = require('mongoose');

var Schema = mongoose.Schema;

var wibbErrorSchema = new Schema(
    {
        occurrenceDescription: String,
        message: String,
        stackTrace: String
    },
    {
        versionKey: false
    });
  

module.exports = mongoose.model('WibbError', wibbErrorSchema);