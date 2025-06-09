
/*
alert(1+2+(3+"abc") +4+5); // 1+2+ = 3
                           // +4+5 = 45

Number("안녕하세요") // not a number 오류

input = number(input);

boolean(조건식) -> !!(조건식) 함축 가능
*/


// 임의의 정수를 입력받아 짝수 혹은 홀수임을 판단 
var run = true;

while (run) {

    input = prompt("임의의 정수를 입력하세요");

    if (isNaN(input)) {

        alert(`입력한 값 : ${input} \n 정수 아님`);

    } else {

        if (input % 2 == 0) {
            alert(`입력한 정수 ${input} \n짝수`);
        } else {
            alert("입력한 정수 : " + input + "\n홀수");
        }

    }
}




