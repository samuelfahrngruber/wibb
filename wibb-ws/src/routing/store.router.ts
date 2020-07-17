import express from 'express';
import { StoreModel } from '../data/schemas/store.schema';
import { WibbErrorHandler, WibbErrorSeverity } from '../util/WibbErrorUtil';

const router = express();

router.get('/', (req, res) => {
    StoreModel.find()
    .exec((err, stores) => {
        if (err) {
            new WibbErrorHandler({
                className: "store.router",
                message: err.message,
                error: err,
                severity: WibbErrorSeverity.ERROR,
            })
            .log()
            .report()
            .respond(res, 500);
        } else if (!stores) {
            res.status(204).json(new Error("NO CONTENT"));
        } else {
            res.json(stores);
        }
    })
});

export const StoreRouter = router;