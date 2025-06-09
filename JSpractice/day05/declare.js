// var는 전역변수 중복선언 => 재정의
var i = 1;
var i = 3;


console.log(i);

// let 는 지역 변수 중복선언 불가 => 에러
let i = 1;
let = 3;


console.log(i);

// 함수의 중복 선언 => 재정의
function a() {
    console.log(1);
}

function a() {
    console.log(2);
}

a();