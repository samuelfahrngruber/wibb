// packages
import express from 'express';
import bodyParser from 'body-parser';
import logger from './middleware/logger.js';
const app = express();

// middleware
app.use(bodyParser.json());
app.use(logger);

// environment
const port = process.env.PORT || 8080; 
process.env.HOST = process.env.HOST || 'http://localhost' + ':' + port;

// routing
import defaultRouter from './routing/default.js';

app.use('/wibb', defaultRouter);

// start
app.listen(port);
console.log('Server started on port ' + port);