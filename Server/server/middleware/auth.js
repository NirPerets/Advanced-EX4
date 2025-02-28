const userService = require('../services/user');
const jwt = require('jsonwebtoken');

const verifyToken = (req, res, next) => {
    const token = req.headers.authorization?.split(' ')[1];
    if (!token) return res.status(401).send('No token provided');

    jwt.verify(token, process.env.JWT_SECRET, (err, decoded) => {
        if (err) return res.status(401).send('Invalid token');

        req.userId = decoded.id;
        next();
    })
}

const verifyManagerToken = (req, res, next) => {
    const token = req.headers.authorization?.split(' ')[1];
    if (!token) return res.status(401).send('No token provided');

    jwt.verify(token, process.env.JWT_SECRET, (err, decoded) => {
        if (err) return res.status(401).send('Invalid token');
        if (!decoded.isManager) return res.status(403).send('Access denied: Managers only');

        req.userId = decoded.id;
        next();
    })
}

module.exports = { verifyToken, verifyManagerToken };