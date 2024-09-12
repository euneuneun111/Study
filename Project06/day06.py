'''
class Circle :
    radius = 10

    def getArea(self) :
        area = 3.141592 * self.radius * self.radius
        return area

    def getCircum(self):
        circum = 2 * 3.148592 * self.radius
        return circum

cir = Circle()

print("반지름의 길이 %d" % cir.radius)
print("원의 면적 : %.2f" % cir.getArea())
print("원주의 길이 : %.2f" % cir.getCircum())
'''

class Person :
    def __init__(self, name):
        self.name = name
        print('%s 님 반갑습니다. ' % name)

person1 = Person("황예린")