import express from 'express';
import { WibbLogger } from '../loggers/logger';
import { WibbErrorModel } from '../data/schemas/wibb-error.schema';

export enum WibbErrorSeverity {
    ERROR,
    WARNING,
}

export interface WibbErrorHandlerOptions {
    className: string;
    message: string;
    error?: Error;
    severity: WibbErrorSeverity;
}

export class WibbErrorHandler {

    constructor(private readonly options: WibbErrorHandlerOptions) {}

    report () {
        const errorModel = new WibbErrorModel({
            occurrenceDescription: `server-side failure in ${this.options.className}`,
            message: this.options.message,
            stackTrace: this.options.error ? JSON.stringify(this.options.error) : "err=null",
        });
        errorModel.save();
        return this;
    }

    log () {
        const message = `[${this.options.className}]: ${this.options.message}`
        switch(this.options.severity) {
            case WibbErrorSeverity.ERROR:
                WibbLogger.logger.error(message, this.options.error);
                break;
            case WibbErrorSeverity.WARNING:
                WibbLogger.logger.warn(message, this.options.error);
                break;
            default:
                WibbLogger.logger.error(message, this.options.error);
                WibbLogger.logger.error('^^^^ this was logged with invalid WibbErrorSeverity ^^^^');
        }
        return this;
    }

    respond (response: express.Response, statusCode: number) {
        response
            .status(statusCode)
            .json(this.options.error);
        return this;
    }
}