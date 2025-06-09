const fs = require('fs');

fs.readFile('none.txt', (error, file) => {

    if(error) {
        console.log('파일 못 읽음 발생');
        console.log(error);
        return;
    }

    console.log(file);
    console.log(file.toString());
});