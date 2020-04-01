// packages
import express from 'express';
import bodyParser from 'body-parser';
import logger from './middleware/logger.js';
import mongoose from 'mongoose';

const app = express();

// middleware
app.use(bodyParser.json());
app.use(logger);

// environment
const port = process.env.PORT || 8080; 
process.env.HOST = process.env.HOST || 'http://localhost' + ':' + port;

// db connection
var connection = mongoose.connect('mongodb://127.0.0.1:27017/wibbdb');

// routing
import defaultRouter from './routing/router-default.js';
import offerRouter from './routing/router-offer.js';
import beerRouter from './routing/router-beer.js';
import storeRouter from './routing/router-store.js';
import reportRouter from './routing/router-report.js';
import wibbErrorRouter from './routing/router-wibberror';
import requestRouter from './routing/router-request';

app.use('/api', defaultRouter);
defaultRouter.use('/offers', offerRouter);
defaultRouter.use('/beers', beerRouter);
defaultRouter.use('/stores', storeRouter);
defaultRouter.use('/reports', reportRouter);
defaultRouter.use('/errors', wibbErrorRouter);
defaultRouter.use('/requests', requestRouter);

app.use(express.static('www'));

// start
app.listen(port);
console.log('Server started on port ' + port);