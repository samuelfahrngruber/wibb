import express from 'express';
import { BeerModel } from '../data/schemas/beer.schema';
import { Error } from 'mongoose';
import { Beer } from '../data/models/beer.model';
import { WibbErrorHandler, WibbErrorSeverity } from '../util/WibbErrorUtil';

const router = express();

router.get('/', (req, res) => {
    BeerModel.find()
    .exec((err: Error, beers: Beer[]) => {
        if (err) {
            new WibbErrorHandler({
                className: "beer.router",
                message: err.message,
                error: err,
                severity: WibbErrorSeverity.ERROR,
            })
            .log()
            .report()
            .respond(res, 500);
        } else if (!beers) {
            res.status(204).json(new Error("NO CONTENT"));
        } else {
            res.json(beers);
        }
    });
});

export const BeerRouter = router;