// packages
import express from 'express';
import offerSchema from '../data/schemas/offerschema';
import mongoConnection from '../data/mongoconnection';

const router = express();

// add routes
router.get('/', function (req, res) {
    let ch = offerSchema.find();
    res.json({changes:ch});
});

export default router;