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

export default router;