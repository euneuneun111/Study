try {
    const array = new Array(-2000);

    alert("!!!!!");
    
} catch (exception) {
    console.log(`${exception.name} 예외 발생`);
} finally {
    console.log(`finally 구문 실행`);
}