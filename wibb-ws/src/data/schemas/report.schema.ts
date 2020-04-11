import { Schema, model } from 'mongoose';

const ReportSchema = new Schema({
    type: String,
    info: String,
    offer: Object,
    solved: Boolean
});

export const ReportModel = model('Report', ReportSchema);