'''color: list = ['blue', 'white', 'red']

print(color[0])

color[0] = 'yellow'

print(color[0])

print(color)

print(color[1:])

numbers = list(range(0,101))
print(numbers)
'''


'''
v = [[1,2,3], [4,5,6], [7,8,9]]

for x in range(0,3):
    for y in range(0,3):
        seq = v[x][y]
        print(seq)
'''

"""
animals = [["사자", "호랑이", "사슴"], ["토끼", "고라니", "다람쥐"]]

index = 0
size = len(animals)
while index < size :
    animal : str = animals[index]
    print(animals)
    index += 1
"""
'''
a = [1,2,3]
b = [4,5,6,7]
c = a + b

print(len(c))
'''

"""
que = ['tr_in', 'b_s', '_axi', 'air_lane']
answers = ['a','u','t','p']
count = 0
tt = 0

for i in range(len(que)) :
    q = que[i]
    ans = input(q)

    if ans == answers[i] :
        print("정답")
        count += 1
    else :
        print("오답")
        tt += 1

print("정답 수", count)
print("오답 수", tt)
"""

'''
flower = ['할미꽃', '델피늄', '장미', '민들레']

flower.append('국화')

print(flower)

flower1 = '다희'

flower.reverse()

new_flower = flower + list(flower1)
print(new_flower)
'''

'''
scores = []
sum = 0

while True :
    score = int(input("성적을 입력하세요"))

    if score < 0 :
        print("종료")
        break

    else :
        scores.append(score)

        for i in range(len(scores)):
            sum += scores[i]

print("점수 합 : ", sum)
print("점수 평균 : ", sum/len(scores))
'''

"""
scores = [[10,20,30], [20,30,40], [30,40,50]]

for a in range(len(scores)) :
    for b in range(len(scores)) :
        print(scores[a][b])
"""

'''
strings = [['잠자리', '풍뎅이', '여치'], ['짜장면', '파스타', '피자', '국수']]
for i in range(len(strings)) :
    for j in range(len(strings[i])) :
        print(strings[i][j])
print()
'''
'''
sum = 0

name = '홍지수'

scores = {'kor': 90, 'eng': 89, 'math': 95, 'science': 88}
print(scores)

scores['kor'] = 70
print(scores['kor'])

scores['music'] = 100
print(scores)

del scores['science']
print(scores)

print('이름 : %s' % name)
print('국어 : %s' % scores['kor'])
print('영어 : %s' % scores['eng'])
print('수학 : %s' % scores['math'])

'''

'''
scores = {'김채린' : 85, '박수정' : 80, '한소희' : 75, '안예린' : 60}

sum = 0

for key in scores :
    sum = sum + scores[key]
    print('%s : %d' %(key , scores[key]))

avg = sum/len(scores)
print('합계 : %d, 평균 : %.2f' %(sum, avg))
'''

def hello() : print("안녕")


hello()
hello()