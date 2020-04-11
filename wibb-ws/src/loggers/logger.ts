import {
    Request,
    Response,
} from "express";
import * as winston from "winston";
import { NextFunction } from "connect";

export const WibbLoggerExpress = (
    req: Request,
    res: Response,
    next: NextFunction
) => {
    WibbLogger.logger.http(
        req.ip + " " + req.method + " " + req.url
    );
    next();
};

export class WibbLogger {
    private static readonly myFormat =
        winston.format.printf(({ level, message, timestamp }) => {
            return `[${timestamp}] ${level}: ${message}`;
        });
    public static logger = winston.createLogger({
        format: winston.format.combine(
            winston.format.colorize(),
            winston.format.cli(),
            winston.format.errors({ stack: true }),
            winston.format.timestamp(),
            WibbLogger.myFormat
        ),
        transports: [
            new winston.transports.Console({
                level: 'http'
            }),
            new winston.transports.File({
                level: 'error',
                filename: './log/wibb.error.log'
            }),
            new winston.transports.File({
                level: 'info',
                filename: './log/wibb.info.log'
            }),
        ]
    });
}