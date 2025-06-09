let id = setInterval(function () {
    console.log("출력합니다.");
}, 1000);

setTimeout(function() {
    clearInterval(id);
}, 3000); 