import express from 'express';
import { ReportModel } from '../data/schemas/report.schema';
import { OfferModel } from '../data/schemas/offer.schema';
import { WibbErrorHandler, WibbErrorSeverity } from '../util/WibbErrorUtil';

const router = express();

router.post('/', (req, res) => {
    // first check if beer an store are actually in the database
    // this ensures that there are no fake POST requests with image links to malicious websites
    // or offers for beers that do not exist
    const report = req.body;
    report.solved = false

    const reportModel = new ReportModel(report);

    let tmpStartDateMin = null;
    let tmpStartDateMax = null
    if (report.offer.startDate) {
        tmpStartDateMin = new Date(report.offer.startDate)
        tmpStartDateMax = new Date(report.offer.startDate)
        tmpStartDateMin.setHours(0, 0, 0, 0)
        tmpStartDateMax.setHours(23, 59, 59, 999)
    }

    let tmpEndDateMin = null
    let tmpEndDateMax = null
    if (report.offer.endDate) {
        tmpEndDateMin = new Date(report.offer.endDate)
        tmpEndDateMax = new Date(report.offer.endDate)
        tmpEndDateMin.setHours(0, 0, 0, 0)
        tmpEndDateMax.setHours(23, 59, 59, 999)
    }

    const offerSchemaSelector = {
        "beer.name": report.offer.beer.name,
        "beer.icon": report.offer.beer.icon,
        "store.name": report.offer.store.name,
        "store.icon": report.offer.store.icon,
        "price": report.offer.price,
        "startDate": tmpStartDateMin === null ? null : {
            "$gte" : tmpStartDateMin,
            "$lte" : tmpStartDateMax
        },
        "endDate": tmpEndDateMin === null ? null : {
            "$gte" : tmpEndDateMin,
            "$lte" : tmpEndDateMax
        },
    };

    Promise.all([
        reportModel.save(),
        OfferModel.deleteOne(offerSchemaSelector)
    ])
    .then(([resAddReport, resDelOffer]) => {
        if(resDelOffer.n === 1 && resDelOffer.deletedCount === 1){
            reportModel.set('solved', true);
            reportModel.save()
                .then((updateresult) => {
                    res.status(200).json(resAddReport);
                });
        }
        else
            res.status(200).json(resAddReport)
    })
    .catch(err => {
        new WibbErrorHandler({
            className: "report.router",
            message: err.message,
            error: err,
            severity: WibbErrorSeverity.ERROR,
        })
        .log()
        .report()
        .respond(res, 500);
    });
});

// TODO: make private
router.get('/', (req, res) => {
    ReportModel.find()
        .exec((err, reports) => {
            if (err) {
                new WibbErrorHandler({
                    className: "report.router",
                    message: err.message,
                    error: err,
                    severity: WibbErrorSeverity.ERROR,
                })
                .log()
                .report()
                .respond(res, 500);
            } else if (reports == null) {
                res.status(204).json(new Error("NO CONTENT"));
            } else {
                res.json(reports);
            }
        });
});

export const ReportRouter = router;