const fs = require('fs');

const a = fs.readFileSync('a.txt');
const b = fs.readFileSync('b.txt');
const c = fs.readFileSync('c.txt');

console.log(a.toString(), b.toString(), c.toString());