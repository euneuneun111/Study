let object = {
    name: '바나나',
    price: 1200
};

console.log(object.name);
console.log(object.price);

for(let key in object) {
    console.log(`${key} : ${object[key]}`);
}