import express from 'express';

import bodyParser from 'body-parser';
import mongoose from 'mongoose';

import { BeerRouter } from './routing/beer.router';
import { StoreRouter } from './routing/store.router';
import { WibbErrorRouter } from './routing/wibb-error.router';
import { DefaultRouter } from './routing/default.router';
import { ReportRouter } from './routing/report.router';
import { RequestRouter } from './routing/request.router';
import { OfferRouter } from './routing/offer.router';
import { WibbLoggerExpress, WibbLogger } from './loggers/logger';

const app = express();

app.use(bodyParser.json());
app.use(WibbLoggerExpress);

const port = process.env.PORT || 8080;
process.env.HOST = process.env.HOST || 'http://localhost' + ':' + port;

mongoose.connect('mongodb://127.0.0.1:27017/wibbdb');

app.use('/api', DefaultRouter);
DefaultRouter.use('/offers', OfferRouter);
DefaultRouter.use('/beers', BeerRouter);
DefaultRouter.use('/stores', StoreRouter);
DefaultRouter.use('/reports', ReportRouter);
DefaultRouter.use('/errors', WibbErrorRouter);
DefaultRouter.use('/requests', RequestRouter);

app.use(express.static('web'));

app.listen(port);
WibbLogger.logger.info('Server started on port ' + port);