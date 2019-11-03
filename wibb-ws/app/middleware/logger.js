module.exports = (req, res, next) => {
    let date = new Date().toLocaleString('en-GB', {
        hour12: false,
        day: '2-digit',
        month: '2-digit',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit'
    });

    console.log('[' + date + '] ' + req.ip + ' ' + req.method + ' ' + req.url);
    next();
};