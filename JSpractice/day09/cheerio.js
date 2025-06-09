const request = require('request');
const cheerio = require('cheerio');

const url = 'https://www.hanbit.co.kr/media/books/new_book_list.html'

request(url, (error, reponse, body) =>{

    const $ = cheerio.load(body);

    $('.book=content').each((index, element) => {
        const title = $(element).find('.book-title').text().trim();
        let writer = $(element).find('.book-author').text().trim();

        writer = writer.split(',').map((item) => item.trim());

        console.log(title);
        console.log(writer);
        console.log();
    })
});
 