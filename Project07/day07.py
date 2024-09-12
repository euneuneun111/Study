'''
import random

count = 1
win = '*'
lose = '*'
win_number = 0
lose_number = 0

for i in range(200) :
    print('-' * 30)
    print("주사위 던지기 : %d 번째" % count)
    me = random.randint(1,6)
    computer = random.randint(1,6)
    print("나 : %d " % me)
    print("컴퓨터 : %d" %computer)

    if me > computer :
        print("나의 승리")
        win += '*'
        win_number += 1

    elif me == computer :
        print("무승부")

    else:
        print("나의 패배")
        lose_number += 1
        lose += '*'

    count += 1

print("나의 승리 횟수 : %d번" %win_number, win)
print("나의 패배 횟수 : %d번" %lose_number, lose)
'''

'''
import random

count = 1

number_1 = '*'
number_2 = '*'
number_3 = '*'
number_4 = '*'
number_5 = '*'
number_6 = '*'

number_1_time = 0
number_2_time = 0
number_3_time = 0
number_4_time = 0
number_5_time = 0
number_6_time = 0


for i in range(100) :
    print('-' * 30)
    print("주사위 던지기 : %d 번째" % count)
    me = random.randint(1,6)

    if me == 1 :
        print("1번")
        number_1 += '*'
        number_1_time += 1

    elif me == 2 :
        print("2번")
        number_2 += '*'
        number_2_time += 1

    elif me == 3 :
        print("3번")
        number_3 += '*'
        number_3_time += 1

    elif me == 4 :
        print("4번")
        number_4 += '*'
        number_4_time += 1

    elif me == 5 :
        print("5번")
        number_5 += '*'
        number_5_time += 1

    else :
        print("6번")
        number_6 += '*'
        number_6_time += 1

    count += 1

print("1번 나온 횟수 : %d번" %number_1_time, number_1)
print("2번 나온 횟수 : %d번" %number_2_time, number_2)
print("3번 나온 횟수 : %d번" %number_3_time, number_3)
print("4번 나온 횟수 : %d번" %number_4_time, number_4)
print("5번 나온 횟수 : %d번" %number_5_time, number_5)
print("6번 나온 횟수 : %d번" %number_6_time, number_6)
'''

import urllib.request
from bs4 import BeautifulSoup

Hollys_stores = list()

for page in range(1,51):
    Hollys_url = f"https://www.hollys.co.kr/store/korea/korStore2.do?pageNo={page}&sido=&gugun=&store="
    # print(Hollys_url)
    html = urllib.request.urlopen(Hollys_url)
    soup_hollys = BeautifulSoup(html, "html.parser")
    tag_t_body = soup_hollys.find('tbody')
    for store in tag_t_body.find_all('tr'):
        if len(store) < 3:
            break
        store_td = store.find_all('td')
        store_name = store_td[1].string
        store_sido = store_td[0].string
        store_assress = store_td[3].string
        store_phone = store_td[5].string

        Hollys_stores.append([store_name] + [store_sido] + [store_assress] + [store_phone])
print(f'Result of Crawling : \r\n{Hollys_stores}')

print((Hollys_stores[0]))



