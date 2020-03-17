// packages
import express from 'express';
import offerSchema from '../data/schemas/offerschema';
import beerSchema from '../data/schemas/beerschema';
import storeSchema from '../data/schemas/storeschema';

const router = express();

// add routes
router.get('/', function (req, res) {
    var today = new Date()
    today.setHours(0,0,0,0)

    // todo fix offer filter with enddate

    offerSchema
    .find({ endDate: { $gte: today } })    
    .sort({ price: 1 })
    .exec((err, offers) => {
        if (err)
            res.status(500).json(err);

        else if (offers == null)
            res.status(204).json(new Error("NO CONTENT"));

        else
            res.json(offers);
    })
});
router.post('/', function (req, res) {
    // first check if beer an store are actually in the database
    // this ensures that there are no fake POST requests with image links to malicious websites
    // or offers for beers that do not exist
    var newOffer = req.body;
    Promise.all([
        beerSchema.findOne({ "name": newOffer.beer.name, "icon": newOffer.beer.icon }),
        storeSchema.findOne({ "name": newOffer.store.name, "icon": newOffer.store.icon })
    ])
    .then(([beers, stores]) => {
        if(beers == null || stores == null || beers.length < 0 || stores.length < 0){
            console.log("potentially malicious offer detected:" );
            console.log(newOffer);
            res.status(400).json(new Error("potentially harmful offer"));
        }
        else {
            // then actually insert the offer into the database
            var o = new offerSchema(newOffer);

            var tmp_endDate_min = new Date(newOffer.endDate)
            var tmp_endDate_max = new Date(newOffer.endDate)
            tmp_endDate_min.setHours(0, 0, 0, 0)
            tmp_endDate_max.setHours(23, 59, 59, 999)

            var offerSchemaSelector = {
                "beer.name": newOffer.beer.name,
                "beer.icon": newOffer.beer.icon,
                "store.name": newOffer.store.name,
                "store.icon": newOffer.store.icon,
                "price": newOffer.price,
                // no start date cuz its not identifying
                "endDate": {
                    "$gte" : tmp_endDate_min, 
                    "$lte" : tmp_endDate_max
                }
            };

            // o.save((err) => {
            //     if(err) res.status(500).json(err);
            //     else res.json(newOffer);
            // });

            offerSchema.findOne(offerSchemaSelector).then(function(offf) {
                if(!offf) o.save((err) => {
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
    .catch(err => {
        console.log("INTERNAL SERVER ERROR: " + err);
        res.status(500).json(err);
    });
});
export default router;