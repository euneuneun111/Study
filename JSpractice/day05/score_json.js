var score = {
    kor : 95,
    eng : 67,
    math : 56,
    scien : 67,
    
    total : function() {
        return this.kor + this.eng + this.math + this.scien;
    },

    avg : function() {
        return this.total() / 4
    }
 }

 console.log(score.avg());