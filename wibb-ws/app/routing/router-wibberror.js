// packages
import express from 'express';
import wibbErrorSchema from '../data/schemas/wibberrorschema';

const router = express();

// add routes
router.post('/', function (req, res) {
    var newError = req.body;
    var o = new wibbErrorSchema(newError);
    o.save((err) => {
        if (err) res.status(500).json(err);
        else res.json(newError);
    });
});

export default router;