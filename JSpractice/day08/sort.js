let arrayA = ['고구마', '감자', '바나나'];

let arrayB = [{
    name: '고구마',
    price: 1000
}, {
    name: '감자',
    price: 500
}, {
    name: '바나나',
    price: 400
}];

arrayA.sort();
console.log(' - 문자열로 정렬');
console.log(arrayA);
console.log(' - 문자열 역순');
console.log(arrayA.reverse());

arrayB.sort((itemA, itemB) => {
    return itemA.price - itemB.price;
});

console.log(arrayB);

arrayB.sort((itemA, itemB) => {
    if (itemA.name < itemB.name) {
        return -1;
    } else if (itemA.name > itemB.name) {
        return 1;
    } else {
        return 0;
    }

});

console.log('- 객체 내부의 문자열로 정렬');
console.log(arrayB);