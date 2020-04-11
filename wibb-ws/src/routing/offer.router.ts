import express from 'express';
import { OfferModel } from '../data/schemas/offer.schema';
import { BeerModel } from '../data/schemas/beer.schema';
import { StoreModel } from '../data/schemas/store.schema';
import { WibbLogger } from '../loggers/logger';
import { Error as MongooseError } from 'mongoose';

const router = express();

router.get('/', (req, res) => {
    const today = new Date()
    today.setHours(0,0,0,0)

    // todo fix offer filter with enddate
    OfferModel
    .find({ endDate: { $gte: today } })
    .sort({ price: 1 })
    .exec((err, offers) => {
        if (err)
            res.status(500).json(err);
        else if (offers == null)
            res.status(204).json(new Error("NO CONTENT"));
        else
            res.json(offers);
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

            const tmpEndDateMin = new Date(newOffer.endDate)
            const tmpEndDateMax = new Date(newOffer.endDate)
            tmpEndDateMin.setHours(0, 0, 0, 0)
            tmpEndDateMax.setHours(23, 59, 59, 999)

            const offerSchemaSelector = {
                "beer.name": newOffer.beer.name,
                "beer.icon": newOffer.beer.icon,
                "store.name": newOffer.store.name,
                "store.icon": newOffer.store.icon,
                "price": newOffer.price,
                // no start date cuz its not identifying
                "endDate": {
                    "$gte" : tmpEndDateMin,
                    "$lte" : tmpEndDateMax
                }
            };

            // o.save((err) => {
            //     if(err) res.status(500).json(err);
            //     else res.json(newOffer);
            // });

            OfferModel.findOne(offerSchemaSelector).then((offf) => {
                if(!offf) offerModel.save((err: MongooseError) => {
                    if(err) res.status(500).json(err);
                    else res.json(newOffer);
                });
                else
                    res.json( {} );
            });

            // offerSchema.findOneAndUpdate(offerSchemaSelector, { $setOnInsert: newOffer }, { upsert: true }, function(err) {
            //     console.log("err: " + err);
            //     if(err) res.status(500).json(err);
            //     else res.json(newOffer);
            // });
        }
    })
    .catch((err) => {
        WibbLogger.logger.error(err.message, err);
        res.status(500).json(err);
    });
});
export const OfferRouter = router;