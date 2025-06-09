/*
const fs = require('fs');

const a = fs.readFileSync('a.txt', (error, file) => {
    console.log(file.toString());
});
const b = fs.readFileSync('b.txt', (error, file) => {
    console.log(file.toString());
});
const c = fs.readFileSync('c.txt', (error, file) => {
    console.log(file.toString());
});
*/

const fs = require('fs');

const a = fs.readFileSync('a.txt', (error, file) => {
    console.log(file.toString());

    const b = fs.readFileSync('b.txt', (error, file) => {
        console.log(file.toString());

        const c = fs.readFileSync('c.txt', (error, file) => {
            console.log(file.toString());
        });

    });

});