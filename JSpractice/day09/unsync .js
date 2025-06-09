const fs = require('fs');

fs.readFileSync('textfile.txt', (error, file) => {
    console.log(file);
    console.log(file.toString());
});


