import express from 'express';
import { RequestModel } from '../data/schemas/request.schema';

const router = express();

router.post('/', (req, res) => {
    const newRequest = req.body;
    const m = new RequestModel(newRequest);
    m.save((err) => {
        if (err) res.status(500).json(err);
        else res.json(newRequest);
    });
});

// TODO: make private
router.get('/', (req, res) => {
    RequestModel.find()
        .exec((err, requests) => {
            if (err)
                res.status(500).json(err);
            else if (requests == null)
                res.status(204).json(new Error("NO CONTENT"));
            else
                res.json(requests);
        });
});

export const RequestRouter = router;