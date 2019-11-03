// packages
import express from 'express';
const router = express();

// add routes
router.get('/', function (req, res) {
    res.json({
        title: 'wibb API',
        github: 'https://github.com/samuelfahrngruber/wibb'
    });
});

export default router;