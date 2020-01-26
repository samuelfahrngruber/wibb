// packages
import express from 'express';
import storeSchema from '../data/schemas/storeschema';
import mongoConnection from '../data/mongoconnection';

const router = express();

// add routes
router.get('/', function (req, res) {
    let ch = storeSchema.find();
    res.json({changes:ch});
});

export default router;