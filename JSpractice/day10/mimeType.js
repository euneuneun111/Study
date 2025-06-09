const express = require('express');
const fs = require('fs');
const path = require('path');

const app = express();

// 다운로드용 공통 함수
function downloadFile(error, data, response, filename) {
    if (error) {
        response.status(404);
        response.send("<script>alert('해당 파일이 존재하지 않습니다.');</script>");
        return;
    }

    response.set({
        'Content-Type': 'application/octet-stream',
        'Content-Disposition': `attachment; filename="${filename}"`
    });
    response.send(data);
}

// 이미지 다운로드 라우터
app.get('/image/:filename', (req, res) => {
    const filename = req.params.filename;
    const filePath = path.join(__dirname, filename);

    fs.readFile(filePath, (err, data) => {
        downloadFile(err, data, res, filename);
    });
});

app.listen(52273, () => {
    console.log('Server running at http://127.0.0.1:52273');
});
