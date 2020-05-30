import express from 'express';
import { WibbLogger } from '../loggers/logger';

const router = express();

router.get('/res/theme/:themename/*', (req, res) => {
    WibbLogger.logger.info("Could not find resource " + req.params[0] + " in theme " + req.params.themename);
    res.redirect(307, '/res/' + req.params[0]);
});

export const StaticResourceInterceptor = router;