let now = new Date();
let before = new Date('April 19, 2000');


let interval = now.getTime() - before.getTime();

interval = Math.floor(interval / (1000 * 60 * 60 * 24));

console.log(`${interval}일째 생존중`);