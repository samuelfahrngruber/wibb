import express from 'express';
import { WibbErrorModel } from '../data/schemas/wibb-error.schema';
import { WibbErrorHandler, WibbErrorSeverity } from '../util/WibbErrorUtil';

const router = express();

router.post('/', (req, res) => {
    const newError = req.body;
    const errorModel = new WibbErrorModel(newError);
    errorModel.save((err) => {
        if (err) res.status(500).json(err);
        else res.json(newError);
    });
});

// todo private
router.get('/', (req, res) => {
    WibbErrorModel.find()
    .exec((err, wibbErrors) => {
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
        } else if (wibbErrors == null) {
            res.status(204).json(new Error("NO CONTENT"));
        } else {
            res.json(wibbErrors);
        }
    });
});

export const WibbErrorRouter = router;