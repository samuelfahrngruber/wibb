// packages
const express = require('express');
const bodyParser = require('body-parser');
const logger = require('./middleware/logger');
const app = express();

// middleware
app.use(bodyParser.json());
app.use(logger);

// environment
const port = process.env.PORT || 8080; 
process.env.HOST = process.env.HOST || 'http://localhost' + ':' + port;

// routing

// start
app.listen(port);
console.log('Server started on port ' + port);