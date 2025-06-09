const request = require('request');
const fs = require('fs');

const url = 'https://www.hanbit.co.kr/media/';

request(url, (error, reponse, body) => {
    // console.log(body);
    fs.writeFile('hanbit.html', body, (error) => {
        console.log("파일 생성 완료");
    });
})

