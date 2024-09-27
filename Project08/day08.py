'''
from audioop import reverse


list_values = [1,2,3,4,5,6,7,8,9,10]

error = 1
while error in list_values :
    list_values.remove(error)

print(list_values)



list_a = [1, 2, 3, 4, 5, 6, 7, 8]

# Use reversed() to get a reversed iterator
reversed_list_a = list(reversed(list_a))  # Convert the iterator to a list
print(reversed_list_a)

# Print elements in the reversed list
for element in reversed_list_a:
    print(element, end=" ")


def print_n_times(n, *args) :
    for i in range(n) :
        for arg in args :
            print(arg)
        print()

print_n_times(3,1,3,4,5,6,7,8)
'''

import numpy as np

'''
# 리스트를 배열로 변환하기 위해서는 리스트로 감싸줘야 합니다
a = np.array([1, 2, 3, 4, 5])  # 리스트로 감싸서 배열 생성

# 임의의 리스트 정의
list_a = [1, 2, 3, 4, 5]

print(type(list_a))  # list_a는 파이썬의 기본 리스트
np_array1 = np.array(list_a)  # 리스트를 넘파이 배열로 변환

print(type(np_array1))  # np_array1은 numpy 배열
print(list_a)  # 파이썬 리스트 출력
print(np_array1)  # numpy 배열 출력

# 2차원 배열 생성
np_array2 = np.array([[1, 2, 3], [4, 5, 6]])
print(np_array2)  # 2차원 배열 출력
'''

'''
np_array3 = np.array([1, 2, 3])
result = np_array3 + 10
print(result)

a = np.arange(0,10,2)
print(a)

b = np.eye(10)
print(10*b)


c = np.array([[1,7,3,5,6],[2,5,514,7,8],[1,2,34,8,5]])
c.sort(axis=0)
c[::-1]
print(c)
'''


a = np.arange(0,100, 1)
print(a)