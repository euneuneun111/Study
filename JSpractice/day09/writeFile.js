const fs = require('fs');

fs.writeFile('output.txt', '안녕하세요...!', (error) => {
    console.log('파일 쓰기를 완료했습니다.');
}); 