import express from 'express';
import { OfferModel } from '../data/schemas/offer.schema';
import { BeerModel } from '../data/schemas/beer.schema';
import { StoreModel } from '../data/schemas/store.schema';
import { WibbLogger } from '../loggers/logger';
import { Error as MongooseError } from 'mongoose';
import { WibbErrorHandler, WibbErrorSeverity, DEFAULT_WIBB_OFFER_TYPE } from '../util/WibbErrorUtil';

const router = express();
const WEEK_MILLISECONDS = 7 * 24 * 60 * 60 * 1000;

router.get('/', (req, res) => {
    const today = new Date()
    today.setHours(0,0,0,0)

    // todo fix offer filter with enddate
    const offerType: string = req.query.type
        ? req.query.type as string
        : DEFAULT_WIBB_OFFER_TYPE;

    OfferModel
    .find({
        $or: [
            { endDate: { $gte: today } },
            { expires: { $gte: today } },
        ],
        type: { $eq: offerType },
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
            const offerType: string = req.query.type
                ? req.query.type as string
                : DEFAULT_WIBB_OFFER_TYPE;
            const offerModel = new OfferModel({
                ...newOffer,
                type: offerType,
                expires: newOffer.endDate ? newOffer.endDate : new Date(new Date().getTime() + WEEK_MILLISECONDS),
            });

            let offerSchemaSelector: any = {
                "beer.name": newOffer.beer.name,
                "beer.icon": newOffer.beer.icon,
                "store.name": newOffer.store.name,
                "store.icon": newOffer.store.icon,
                "price": newOffer.price,
                "type": newOffer.type,
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