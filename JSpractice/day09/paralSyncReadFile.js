const fs = require('fs');

const async = require('async');

async.parallel([
    (callback) => {fs.readFile('a.txt',callback);}, 
    (callback) => {fs.readFile('b.txt',callback);}, 
    (callback) => {fs.readFile('c.txt',callback);}, 
], (error, results) =>  {
    console.log(results);
});