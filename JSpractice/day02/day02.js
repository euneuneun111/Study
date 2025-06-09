/*
let date = new Date(); 

console.log("연도:", date.getFullYear());
console.log("월:", date.getMonth() + 1);  // 0부터 시작 → +1 필요
console.log("일:", date.getDate());
console.log("요일(0=일):", date.getDay());
console.log("시:", date.getHours());
console.log("분:", date.getMinutes());
console.log("초:", date.getSeconds());


if(date.getHours() < 12) {
    console.log("오전");
} else {
    console.log("오후");
}

*/

let judge = true;

let id = prompt("아이디를 입력하세요");
let paw = prompt("패스워드를 입력하세요");

judge = id == "mimi" && pwd == "mimi" ;

if (judge) {
    alert("로그인 성공")
}  else {
    alert("아이디 혹은 패스워드가 일치하지 않음")
}
