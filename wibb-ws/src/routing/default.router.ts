import express from 'express';

const router = express();

router.get('/', (req, res) => {
    res.json({
        title: 'wibb API',
        github: 'https://github.com/samuelfahrngruber/wibb'
    });
});

export const DefaultRouter = router;