'''
def big_number(shot1 : int, shot2 : int, shot3 : int) :

    if shot1 > shot2 > shot3:
        print("가장 큰 수는 ", shot1)
    elif shot1 > shot3 > shot2 :
        print("가장 큰 수는 ", shot1)
    elif shot2 > shot1 > shot3 :
        print("가장 큰 수는 ", shot1)
    elif shot2 > shot3 > shot1 :
        print("가장 큰 수는 ", shot1)
    elif shot3 > shot1 > shot2 :
        print("가장 큰 수는 ", shot1)
    elif shot3 > shot2 > shot1 :
        print("가장 큰 수는 ", shot1)

shot1 = int(input("첫번째 숫자 입력"))
shot2 = int(input("두번째 숫자 입력"))
shot3 = int(input("세번째 숫자 입력"))
big_number(shot1,shot2,shot3)
'''

'''
def number_one() :
    shot = []

    for i in range(3) :
        shot.append(int(input("숫자 입력: ")))

    shot.sort()
    shot.reverse()

    print("가장 큰 수는", shot[0])

number_one()
'''

'''
def small_timer(shot1: int, shot2: int):
    ball1 = []
    ball2 = []
    account = []

    # 첫 번째 숫자의 배수 구하기
    for i in range(1, 100):
        ball1.append(shot1 * i)

    # 두 번째 숫자의 배수 구하기
    for a in range(1, 100):
        ball2.append(shot2 * a)

    # 공통 배수를 찾기
    for num1 in ball1:
        if num1 in ball2:
            account.append(num1)

    # 공통 배수 리스트가 비어있지 않으면 최소 공배수 출력
    if account:
        account.sort()
        print("최소 공배수 =", account[0])
    else:
        print("공통 배수가 없습니다.")

# 사용자로부터 두 숫자를 입력받음
shot1 = int(input("첫번째 숫자 입력: "))
shot2 = int(input("두번째 숫자 입력: "))
small_timer(shot1, shot2)
'''

'''
file = open(file="sample.txt", encoding="UTF-8", mode='a')
file.write("안녕하세요")
file.write("\n")

file.close()

file = open(file="sample.txt", mode='r', encoding="UTF-8")
lines = file.readlines()
print(lines)

for line in lines :
    print(line, end="")

file.close()
'''

'''
def nanu_fun(shot : int) :
    if shot % 2 == 0 :
        print(shot ,"은 2로 나누어 떨어지는 숫자입니다. ")
    else :
        print(shot ,"은 2로 나누어 떨어지는 숫자가 아닙니다. ")

    if shot % 3 == 0 :
        print(shot ,"은 3으로 나누어 떨어지는 숫자 입니다.")
    else:
        print(shot ,"은 3으로 나누어 떨어지는 숫자가 아닙니다. ")

    if shot % 4 == 0:
        print(shot ,"은 4로 나누어 떨어지는 숫자 입니다.")
    else:
        print(shot ,"은 4로 나누어 떨어지는 숫자가 아닙니다. ")

    if shot % 5 == 0 :
        print(shot ,"은 5로 나누어 떨어지는 숫자 입니다.")
    else:
        print(shot ,"은 5로 나누어 떨어지는 숫자가 아닙니다. ")

shot = int(input("숫자 입력"))
nanu_fun(shot)
'''

'''
numbers = [273,103,5,32,65,9,72,8000,99]

for i in range(len(numbers)) :
    if numbers[i] % 2 == 0 :
            print(numbers[i], "은(는) 짝수 입니다.")
    else :
            print(numbers[i], "은(는) 홀수 입니다.")

for i in range(len(numbers)) :
    if 999 > numbers[i] > 100  :
            print(numbers[i], "3자리수 입니다.")
    elif 100 > numbers[i] > 10 :
            print(numbers[i], "2자리수 입니다.")
    elif 10 > numbers[i] >= 1 :
            print(numbers[i], "1자리수 입니다.")
    else :
            print(numbers[i], "4자리수 이상 입니다.")
'''


numbers = []

for i in range(1, 100) :
    numbers.append(i * (100 - i))
    i += 1

    numbers.sort()
    numbers.reverse()

print(numbers[0])


