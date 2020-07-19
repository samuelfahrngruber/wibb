import express from 'express';

const router = express();

router.get('/', (req, res) => {
    res.json({
        title: 'wibb API',
        github: 'https://github.com/samuelfahrngruber/wibb',
        version: '1.0.7',
        minClientVersionCode: 6,
    });
});

export const DefaultRouter = router;