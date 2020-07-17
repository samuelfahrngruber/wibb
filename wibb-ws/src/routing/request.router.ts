import express from 'express';
import { RequestModel } from '../data/schemas/request.schema';
import { WibbErrorHandler, WibbErrorSeverity } from '../util/WibbErrorUtil';

const router = express();

router.post('/', (req, res) => {
    const newRequest = req.body;
    const m = new RequestModel(newRequest);
    m.save((err) => {
        if (err) {
            new WibbErrorHandler({
                className: "request.router",
                message: err.message,
                error: err,
                severity: WibbErrorSeverity.ERROR,
            })
            .log()
            .report()
            .respond(res, 500);
        } else {
            res.json(newRequest);
        }
    });
});

// TODO: make private
router.get('/', (req, res) => {
    RequestModel.find()
        .exec((err, requests) => {
            if (err) {
                new WibbErrorHandler({
                    className: "request.router",
                    message: err.message,
                    error: err,
                    severity: WibbErrorSeverity.ERROR,
                })
                .log()
                .report()
                .respond(res, 500);
            } else if (requests == null) {
                res.status(204).json(new Error("NO CONTENT"));
            } else {
                res.json(requests);
            }
        });
});

export const RequestRouter = router;