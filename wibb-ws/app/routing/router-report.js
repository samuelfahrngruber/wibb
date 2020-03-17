import express from 'express';
import reportSchema from '../data/schemas/reportschema';
import offerSchema from '../data/schemas/offerschema';

const router = express();

// add routes
router.post('/', function (req, res) {
    // first check if beer an store are actually in the database
    // this ensures that there are no fake POST requests with image links to malicious websites
    // or offers for beers that do not exist
    var report = req.body;
    report.solved = false

    var rscheme = new reportSchema(report);

    var tmp_startDate_min = new Date(report.offer.startDate)
    var tmp_startDate_max = new Date(report.offer.startDate)
    tmp_startDate_min.setHours(0, 0, 0, 0)
    tmp_startDate_max.setHours(23, 59, 59, 999)

    var tmp_endDate_min = new Date(report.offer.endDate)
    var tmp_endDate_max = new Date(report.offer.endDate)
    tmp_endDate_min.setHours(0, 0, 0, 0)
    tmp_endDate_max.setHours(23, 59, 59, 999)

    var offerSchemaSelector = {
        "beer.name": report.offer.beer.name,
        "beer.icon": report.offer.beer.icon,
        "store.name": report.offer.store.name,
        "store.icon": report.offer.store.icon,
        "price": report.offer.price,
        "startDate": {
            "$gte" : tmp_startDate_min, 
            "$lte" : tmp_startDate_max
        },
        "endDate": {
            "$gte" : tmp_endDate_min, 
            "$lte" : tmp_endDate_max
        },
    }
    Promise.all([
        rscheme.save(),
        offerSchema.deleteOne(offerSchemaSelector)
    ])
    .then(([resAddReport, resDelOffer]) => {
        if(resDelOffer.n == 1 && resDelOffer.deletedCount == 1){
            rscheme.solved = true
            rscheme.save()
            .then((updateresult) => {
                res.status(200).json(resAddReport);
            });
        }
        else
            res.status(200).json(resAddReport)
    })
    .catch(err => {
        res.status(500).json(err);
    });
});

// TODO: make private
router.get('/', function (req, res) {
    reportSchema.find()
    .exec((err, reports) => {
        if (err)
            res.status(500).json(err);

        else if (reports == null)
            res.status(204).json(new Error("NO CONTENT"));
            
        else
            res.json(reports);
    })
});

export default router;