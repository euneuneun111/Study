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

function downloadFile(error, data, response, filename) {
   if (error) {
        response.status(404);
        response.send("<script>alert('해당 파일이 존재하지 않습니다.');</script>");
        return;
    }

      response.set({
        'content=type' : 'application/octet-stream',
        'content-disposition' : 'attachment',
        'filename' : 'image.png'  
    });
    response.send(data);
}