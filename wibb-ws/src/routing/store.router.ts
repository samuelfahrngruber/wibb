import express from 'express';
import { StoreModel } from '../data/schemas/store.schema';

const router = express();

router.get('/', (req, res) => {
    StoreModel.find()
    .exec((err, stores) => {
        if (err)
            res.status(500).json(err);
        else if (!stores)
            res.status(204).json(new Error("NO CONTENT"));
        else
            res.json(stores);
    })
});

export const StoreRouter = router;