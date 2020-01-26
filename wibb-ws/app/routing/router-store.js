// packages
import express from 'express';
import storeSchema from '../data/schemas/storeschema';

const router = express();

// add routes
router.get('/', function (req, res) {
    storeSchema.find()
    .exec((err, stores) => {
        if (err)
            res.status(500).json(err);

        else if (stores == null || stores.length < 1)
            res.status(204).json(new Error("NO CONTENT"));

        else
            res.json(stores);
    })
});

export default router;