// packages
import express from 'express';
import bodyParser from 'body-parser';
import logger from './middleware/logger.js';
const app = express();

// middleware
app.use(bodyParser.json());
app.use(logger);

// environment
const port = process.env.PORT || 8080; 
process.env.HOST = process.env.HOST || 'http://localhost' + ':' + port;

// routing
import defaultRouter from './routing/router-default.js';
import offerRouter from './routing/router-offer.js';
import beerRouter from './routing/router-beer.js';
import storeRouter from './routing/router-store.js';

app.use('/api', defaultRouter);
defaultRouter.use('/offers', offerRouter);
defaultRouter.use('/beers', beerRouter);
defaultRouter.use('/stores', storeRouter);

app.use(express.static('www'))

// start
app.listen(port);
console.log('Server started on port ' + port);