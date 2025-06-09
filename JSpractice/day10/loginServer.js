const express = require('express');
const fs = require('fs');

const app = express();

app.get('/login', (req,res)=> {
    fs.readFile('loginform.html', (error, data) =>{
        res.type("text/html");
        res.send(data.toString());
    });
});
app.post('/login',(req,res)=> {
    console.log(req.params.id);
    console.log(req.params.pwd);
    console.log(req.body.id);
    console.log(req.body.pwd);

    let id = req.body.id;
    let pwd = req.body.pwd;

    if(!(id && id == 'mimi')) {
        res.send("<script>alert('아이디가 일치하지 않습니다.');"
            +"location.href='login';"
            +"<script>"
        )
        return;
    }   
    if(!(pwd && pwd == 'mimi')) {
          res.send("<script>alert('아이디가 일치하지 않습니다.');"
            +"location.href='login';"
            +"<script>"
          )
          return;
    }

    res.redirect("main");
});

app.use(express.urlencoded({extended:false}));

app.get('./main', (req,res)=> {
    res.send("<h1>메인입니다.<h1>");
});

app.listen(52273,() => {
    console.log('server running');
});