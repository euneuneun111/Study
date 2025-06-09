const express = require('express');

const app = express();

app.get('/image/:filename', (request, response) => {
    let filename = request.params.filename;
    
    fs.readFile(filename, (error,data) => {
        downloadFile(error,data,response,filename);
    });
});

app.get('/audio', (request, response) => {
    fs.readFile('audio.mp3', (error,data) => {
        downloadFile(error,data,response,filename);
    });
});

app.listen(52273,() => {
    console.log('server running at http://127.0.0.1:52273');
});

app.get('*', (requerst ,response) => {
    
    response.status(404);
    let result = '<script'
            + 'alert("네이버로 이동합니다.");'
            + 'location.href="https://www.naver.com";'
            + '</script>'
    
    //response.redirect("https://wwww.naver.com");
}); 