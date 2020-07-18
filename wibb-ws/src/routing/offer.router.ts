import express from 'express';
import { OfferModel } from '../data/schemas/offer.schema';
import { BeerModel } from '../data/schemas/beer.schema';
import { StoreModel } from '../data/schemas/store.schema';
import { WibbLogger } from '../loggers/logger';
import { Error as MongooseError } from 'mongoose';
import { WibbErrorHandler, WibbErrorSeverity } from '../util/WibbErrorUtil';

const router = express();

router.get('/', (req, res) => {
    const today = new Date()
    today.setHours(0,0,0,0)

    // todo fix offer filter with enddate
    OfferModel
    .find({
        $or: [
            { endDate: { $gte: today } },
            { endDate: { $eq: null } },
        ],
    })
    .sort({ price: 1 })
    .exec((err, offers) => {
        if (err) {
            new WibbErrorHandler({
                className: "offer.router",
                message: err.message,
                error: err,
                severity: WibbErrorSeverity.ERROR,
            })
            .log()
            .report()
            .respond(res, 500);
        } else if (offers == null) {
            res.status(204).json(new Error("NO CONTENT"));
        } else {
            res.json(offers);
        }
    });
});
router.post('/', (req, res) => {
    // first check if beer an store are actually in the database
    // this ensures that there are no fake POST requests with image links to malicious websites
    // or offers for beers that do not exist
    const newOffer = req.body;
    Promise.all([
        BeerModel.findOne({ "name": newOffer.beer.name, "icon": newOffer.beer.icon }),
        StoreModel.findOne({ "name": newOffer.store.name, "icon": newOffer.store.icon })
    ])
    .then(([beers, stores]) => {
        if(!beers || !stores) {
            WibbLogger.logger.warn("potentially malicious offer detected!", newOffer);
            res.status(400).json(new Error("potentially harmful offer"));
        }
        else {
            // then actually insert the offer into the database
            const offerModel = new OfferModel(newOffer);

            let offerSchemaSelector: any = {
                "beer.name": newOffer.beer.name,
                "beer.icon": newOffer.beer.icon,
                "store.name": newOffer.store.name,
                "store.icon": newOffer.store.icon,
                "price": newOffer.price,
            };

            if(newOffer.endDate) {
                const tmpEndDateMin = new Date(newOffer.endDate)
                const tmpEndDateMax = new Date(newOffer.endDate)
                tmpEndDateMin.setHours(0, 0, 0, 0)
                tmpEndDateMax.setHours(23, 59, 59, 999)
                offerSchemaSelector = {
                    ...offerSchemaSelector,
                    "endDate": {
                        "$gte" : tmpEndDateMin,
                        "$lte" : tmpEndDateMax
                    }
                };
            }

            OfferModel.findOne(offerSchemaSelector).then((offf) => {
                if(!offf) offerModel.save((err: MongooseError) => {
                    if(err) {
                        new WibbErrorHandler({
                            className: "offer.router",
                            message: err.message,
                            error: err,
                            severity: WibbErrorSeverity.ERROR,
                        })
                        .log()
                        .report()
                        .respond(res, 500);
                    }
                    else res.json(newOffer);
                });
                else
                    res.json( {} );
            });
        }
    })
    .catch((err) => {
        new WibbErrorHandler({
            className: "offer.router",
            message: err.message,
            error: err,
            severity: WibbErrorSeverity.ERROR,
        })
        .log()
        .report()
        .respond(res, 500);
    });
});
export const OfferRouter = router;