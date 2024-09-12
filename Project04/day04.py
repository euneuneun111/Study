'''
def funtion(param_name) :
    result = param_name + 10
    print(result)

funtion(param_name=20)
'''
'''
def funtional_funtion(param: int) -> int:
    result = param + 1_000
    return result

result = funtional_funtion(param = 100)
print(result)

def show_funtion() :
    print("잔액 : 2000")
    print("날짜 : 2000.4.19")
    print("잔액 : 2000")
    print("잔액 : 2000")


show_funtion()
'''

'''
def supply_funtion() -> tuple :
    (x,y) = (10, 20)
    print(sum)
    return (x,y)


def swapping_values(value1: int, value2: int) -> tuple:
    return (value2, value1)

result = swapping_values(1_234,1_456)
print(result)
'''

'''
def hello() :
    print("hello")

hello()
'''

'''
def jjack(number: int) :
    if number % 2 == 0 :
        print("짝수")
    else :
        print("홀수")

jjack(number=3)
'''



'''
def sum_function(start: int, end: int):
    total_sum = 0

    for i in range(start, end + 1):
        total_sum += i

    return total_sum

result = sum_function(2, 5)
print(result)
'''

'''
def time_function(shot : int) :

    if shot % 2 == 0 :
        print("2의 배수")
    elif shot % 3 == 0 :
        print("3의 배수")
    elif shot % 5 == 0 :
        print("5의 배수")

    elif shot % 7 == 0:
        print("7의 배수")

    elif shot % 11 == 0:
        print("11의 배수")

time_function(int(input("정수 입력")))
'''

'''
def onenb_function (shot : float) :

    nb = shot * shot * 3.14
    print("넓이 : ", nb)
    h = float(input("높이 입력"))
    bp = nb * h
    gp = shot * shot * shot * 3.14 * 4 / 3


    print("부피 : ", bp)
    print("구의 부피 : ", gp)

onenb_function(float(input("숫자 입력")))
'''

'''
def common_divisors(shot1: int, shot2: int):
    divisors = []

    for i in range(1, min(shot1, shot2) + 1):
        if shot1 % i == 0 and shot2 % i == 0:
            divisors.append(i)

    print(f"{shot1}와 {shot2}의 공약수: {divisors}")


# 함수 실행
shot1 = int(input("첫 번째 숫자 입력: "))
shot2 = int(input("두 번째 숫자 입력: "))
common_divisors(shot1, shot2)
'''

'''
def sosu_function(shot : int) :

    i = 1
    count = 0

    for i in range(i, shot + 1 ) :

        if  shot % i == 0 :
            count += 1
    i += 1

    if count == 2 :
        print(shot, "은 소수입니다.")
    else :
        print(shot, "은 소수가 아닙니다.")

sosu_function(int(input("숫자 입력")))
'''

'''
# 짝수의 합
def couple_sum(shot : int) :
    i = 1
    sum = 0
    for i in range(i, shot + 1) :
        if i % 2 == 0 :
            sum += i
    i += 1

    print (shot, "까지 짝수들의 합", sum)

couple_sum(int(input("숫자 입력")))
'''
'''
# 문자열 뒤집기
def reverse_string(s: str):
    # 문자열을 거꾸로 뒤집기
    reversed_s = s[::-1]
    print("뒤집어진 문자열:", reversed_s)

# 사용자 입력을 받아서 함수 실행
user_input = input("문자열을 입력하세요: ")
reverse_string(user_input)
'''

'''
#곱셈
def multi_fun(shot : int) :
    i = 1
    for i in range(i, shot +1) :
        print(shot, "*", i , "=", shot*i)

    i += 1

multi_fun(int(input("숫자 입력")))
'''

'''
def remove_duplicates_and_sort(numbers: list):
    # 중복 제거
    unique_numbers = set(numbers)

    # 오름차순으로 정렬
    sorted_numbers = sorted(unique_numbers)

    return sorted_numbers

# 함수 실행
user_input = input("정수 리스트를 입력하세요 (쉼표로 구분): ")
# 문자열을 리스트로 변환
numbers_list = [int(x) for x in user_input.split(',')]
result = remove_duplicates_and_sort(numbers_list)

print("중복 제거 및 정렬된 리스트:", result)
'''