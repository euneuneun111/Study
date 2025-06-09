const express = require('express');
const fs = require('fs');

const app = express();
app.use(express.static('./template/static'));

app.get('/', (req, res) => {
    let filename = "main.html"
    let view = viewResolver(filename);

    res.send(view);
});

app.get('/menu1/main',(req,res)=>{
    res.send(viewResolver('menu1.html'));
});
app.get('/menu2/main',(req,res)=>{
    res.send(viewResolver('menu2.html'));
});
app.get('/menu3/main',(req,res)=>{
    res.send(viewResolver('menu3.html'));
});
app.get('/menu4/main',(req,res)=>{
    res.send(viewResolver('menu4.html'));
});
app.get('/menu5/main',(req,res)=>{
    res.send(viewResolver('menu5.html'));
});

app.listen(52273, () => {
    console.log("server started..");
});

function viewResolver(filename, response) {
    let view = "";

    view += fs.readFileSync('./template/header.html').toString()
         +fs.readFileSync('./template/' + filename).toString()
         +fs.readFileSync('./template/footer.html').toString()

    return view;
}