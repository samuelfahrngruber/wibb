var mongoose = require('mongoose');

var connection = mongoose.connect('mongodb://127.0.0.1:27017/wibbdb');

module.exports = connection;