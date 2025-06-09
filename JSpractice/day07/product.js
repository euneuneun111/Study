class product {
    constructor(name, price) {
        this.name = name
        this.price = price
    }

    print() {
        console.log(`${this.name}의 가격은 ${this.price}원 입니다.`);
    }
}