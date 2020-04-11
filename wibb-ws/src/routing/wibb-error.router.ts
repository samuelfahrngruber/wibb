import express from 'express';
import { WibbErrorModel } from '../data/schemas/wibb-error.schema';

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
        if (err)
            res.status(500).json(err);
        else if (wibbErrors == null)
            res.status(204).json(new Error("NO CONTENT"));
        else
            res.json(wibbErrors);
    });
});

export const WibbErrorRouter = router;