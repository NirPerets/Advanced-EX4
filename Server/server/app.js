const express    = require('express');
const bodyParser = require('body-parser');
const cors       = require('cors');
const mongoose   = require('mongoose');
const jwt        = require('jsonwebtoken');
const categories = require('./routes/category');
const movies     = require('./routes/movies')
const users      = require('./routes/user')
const path       = require('path')
const User = require('./models/User');

require('custom-env').env(process.env.NODE_ENV, './config');
mongoose.connect(process.env.CONNECTION_STRING, {});

var app = express();
app.use(express.static('public'));
app.use(cors());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(express.json());
app.use('/image', express.static(path.join(__dirname, 'public/image')))
app.use('/video', express.static(path.join(__dirname, 'public/video')))
app.use('/profile', express.static(path.join(__dirname, 'public/profile')))
app.use('/api/categories', categories)
app.use('/api/movies', movies)
app.use('/api/users', users)


// THIS IS THE LOGIN ROUTE
app.post('/api/tokens', async (req, res) => {
    try {
        const user = await User.findOne({ email: req.body.email, password: req.body.password })
        if (!user) return res.status(400).send('Incorrect credentials')

        const token = jwt.sign({ id: user._id, isManager: user.isManager }, process.env.JWT_SECRET);
        return res.status(200).json({ token })
    } catch (error) {
        console.log(error)
        res.status(400).send('Incorrect credentials')
    }
})

app.listen(process.env.PORT);
