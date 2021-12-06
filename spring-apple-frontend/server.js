const express = require('express');
const request = require('request');

const app = express();
const port = 3000;

const SERVICE_ENDPOINT = process.env.SERVICE_ENDPOINT || "http://localhost:8080";

// set the view engine to ejs
app.set('view engine', 'ejs');

app.use(express.static(__dirname + '/public'));

app.get('/', (req, res) => {
    res.redirect('/products');
})

app.get('/products', (req, res) => {
    request({
        url: `${SERVICE_ENDPOINT}/products`,
        method: 'GET'
    }, (err, r, body) => {
        let items = JSON.parse(body);
        items = items.map((item) => {
            return {
                id: item.id,
                images: item.images,
                title: item.title,
                price: item.price,
                currency: "USD"
            }
        })
        res.render("user/productBrowser", {items});
    })
})

app.get('/products/:id', (req, res) => {
    request({
        url: `${SERVICE_ENDPOINT}/products/${req.params.id}`,
        method: 'GET'
    }, (err, r, body) => {
        let item = JSON.parse(body);

        let reviews = [{
            rating: 4,
            createdAt: "January 28, 2020",
            author: "Test User",
            image: "https://mdbootstrap.com/img/Photos/Others/placeholder1.jpg",
            text: "this is a comment"
        },{
            rating: 4,
            createdAt: "January 28, 2020",
            author: "Test User",
            image: "https://mdbootstrap.com/img/Photos/Others/placeholder1.jpg",
            text: "this is a comment"
        },{
            rating: 4,
            createdAt: "January 28, 2020",
            author: "Test User",
            image: "https://mdbootstrap.com/img/Photos/Others/placeholder1.jpg",
            text: "this is a comment"
        }]
        res.render("user/productDetail", {item, reviews});
    })
})

app.get('/shoppingCart', (req, res) => {

    let items = [{
        rating:5,
        images: [
            'https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/macbook-air-segment?wid=800&hei=600&fmt=png-alpha&qlt=80&.v=1573580916082',
            'https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/macbook-air-segment?wid=800&hei=600&fmt=png-alpha&qlt=80&.v=1573580916082',
            'https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/macbook-air-segment?wid=800&hei=600&fmt=png-alpha&qlt=80&.v=1573580916082'
        ],
        title: "MacBook Air",
        subtitle: "sub-category",
        price: 35000,
        currency: "USD",
        quantity: 2
    },{
        rating:5,
        images: [
            'https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/macbook-air-segment?wid=800&hei=600&fmt=png-alpha&qlt=80&.v=1573580916082',
            'https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/macbook-air-segment?wid=800&hei=600&fmt=png-alpha&qlt=80&.v=1573580916082',
            'https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/macbook-air-segment?wid=800&hei=600&fmt=png-alpha&qlt=80&.v=1573580916082'
        ],
        title: "MacBook Air",
        subtitle: "sub-category",
        price: 35000,
        currency: "USD",
        quantity: 2
    }]

    res.render("user/shoppingCart", {items});
})

app.get('/login', (req, res) => {
    res.render("user/login");
})

app.get('/orders', (req, res) => {

    let items = [{
        images: ['https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/macbook-air-segment?wid=800&hei=600&fmt=png-alpha&qlt=80&.v=1573580916082'],
        title: "MacBook Air",
        price: 35000,
        currency: "USD"
    },
    {
        images: ['https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/macbook-pro-segment-2019?wid=800&hei=600&fmt=png-alpha&qlt=80&.v=1573580916135'],
        title: "MacBook Pro",
        price: 42900,
        currency: "USD"
    },
    {
        images: ['https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/imac-segment?wid=800&hei=600&fmt=png-alpha&qlt=80&.v=1570232081431'],
        title: "iMac",
        price: 37900,
        currency: "USD"
    },
    {
        images: ['https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/imac-pro-segment?wid=800&hei=600&fmt=png-alpha&qlt=80&.v=1570231926191'],
        title: "iMac Pro",
        price: 172900,
        currency: "USD"
    },
    {
        images: ['https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/mac-pro-segment?wid=800&hei=600&fmt=png-alpha&qlt=80&.v=1524766968633'],
        title: "Mac Pro",
        price: 114900,
        currency: "USD"
    },
    {
        images: ['https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/mac-mini-segment?wid=800&hei=600&fmt=png-alpha&qlt=80&.v=1539466285370'],
        title: "Mac mini",
        price: 27900,
        currency: "USD"
    }]
    res.render("user/orders", {items: items});
})

app.listen(port, () => {
    console.log(`Example app listening at http://localhost:${port}`);
})