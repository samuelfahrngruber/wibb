// packages
import express from 'express';
import requestSchema from '../data/schemas/requestschema';

const router = express();

// add routes
router.post('/', function (req, res) {
    var newRequest = req.body;
    var o = new requestSchema(newRequest);
    o.save((err) => {
        if (err) res.status(500).json(err);
        else res.json(newRequest);
    });
});

// TODO: make private
router.get('/', function (req, res) {
    requestSchema.find()
    .exec((err, requests) => {
        if (err)
            res.status(500).json(err);

        else if (requests == null)
            res.status(204).json(new Error("NO CONTENT"));
            
        else
            res.json(requests);
    })
});

export default router;