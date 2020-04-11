import express, { Router } from 'express';
import { BeerModel } from '../data/schemas/beer.schema';
import { Error } from 'mongoose';
import { Beer } from '../data/models/beer.model';
import { WibbLogger } from '../loggers/logger.js';

const router = express();

router.get('/', (req, res) => {
    BeerModel.find()
    .exec((err: Error, beers: Beer[]) => {
        if (err) {
            WibbLogger.logger.error(err.message, err);
            res.status(500).json(err);
        } else if (!beers) {
            res.status(204).json(new Error("NO CONTENT"));
        } else {
            res.json(beers);
        }
    });
});

export const BeerRouter = router;