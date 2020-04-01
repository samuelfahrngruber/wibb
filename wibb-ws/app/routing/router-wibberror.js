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

// TODO: make private
router.get('/', function (req, res) {
    wibbErrorSchema.find()
    .exec((err, werrors) => {
        if (err)
            res.status(500).json(err);

        else if (werrors == null)
            res.status(204).json(new Error("NO CONTENT"));
            
        else
            res.json(werrors);
    })
});

export default router;