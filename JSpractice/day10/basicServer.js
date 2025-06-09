const express = require('express');

const app = express();

app.use((request, response)=> {
    response.send('<h1>hello express : 노은광 </h1>');
});


app.listen(52273,() => {
    console.log('server running at http://127.0.0.1:52273');
});