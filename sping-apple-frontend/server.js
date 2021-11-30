const express = require('express');
const app = express();
const port = 3000;

// set the view engine to ejs
app.set('view engine', 'ejs');

app.use(express.static(__dirname + '/public'));

app.get('/', (req, res) => {

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
    res.render("user/productBrowser", {items: items});
})

app.get('/detail', (req, res) => {

    let item = {
        images: ['https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/macbook-air-segment?wid=800&hei=600&fmt=png-alpha&qlt=80&.v=1573580916082'],
        title: "MacBook Air",
        price: 35000,
        currency: "USD",
        description: `Super compact yoga mat with attached straps
        Perfect for everyday carry and convenience
        Weighs just 2.1 lbs, packs to a newspaper size
        Origami folding, secures with attached straps that make a handle
        Fits in a backpack or small bags
        Practice surface folds top to top and never touches the bottom
        Use attached straps straps to wash then hang to dry in the shower
        Slim natural rubber packs small but is surprisingly supportive
        Incredibly-sticky grip
        One food-producing tree planted per mat in Africa
        Due to virus concerns, we are unable to accept returns of this product at this time`
    }
    res.render("user/productDetail", {item});
})

app.listen(port, () => {
    console.log(`Example app listening at http://localhost:${port}`);
})