const request = require('request');
const cheerio = require('cheerio');
const fs = require('fs');

const url = 'https://www.lotteeatz.com/event/main';

request(url, (error, response, body) => {

    const $ = cheerio.load(body);

    let html = `
    <!DOCTYPE html>
    <html lang="ko">
    <head>
        <meta charset="UTF-8">
        <title>롯데이츠 이벤트</title>
    </head>
    <body>
        <h1>진행 중인 이벤트</h1>
        <ul>
    `;

    $('.grid-info-box').each((index, element) => {
        const title = $(element).find('.grid-title').text().trim();
        const period = $(element).find('.grid-period').text().trim();

        html += `
        <li>
            <h4>${title}</h4>
            기간: ${period}
        </li>
        `;
    });

    html += `
            </ul>
            </body>
            </html>
            `;

    fs.writeFile('lotte_event.html', html, (error) => {
        if (!error) {
            console.log('HTML 파일 저장 완료: lotte_event.html');
        }

    });
});
