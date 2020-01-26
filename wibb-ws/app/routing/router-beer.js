// packages
import express from 'express';
import beerSchema from '../data/schemas/beerschema';

const router = express();

// add routes
router.get('/', function (req, res) {
    beerSchema.find()
    .exec((err, beers) => {
        if (err)
            res.status(500).json(err);

        else if (beers == null || beers.length < 1)
            res.status(204).json(new Error("NO CONTENT"));

        else
            res.json(beers);
    })
});

export default router;