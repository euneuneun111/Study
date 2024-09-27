'''
import numpy as np


a = np.array([[1,2], [1,-3]])
b = np.array([6,1])

s = np.linalg.solve(a,b)

print(s)
'''
from ast import Index
from tkinter.font import names

import matplotlib as mpl
import matplotlib.pyplot as plt
import numpy as np
from fontTools.misc.cython import returns

'''
a = [1,2,3,4,5]
plt.plot(a)

plt.show()
'''
'''
import numpy as np
import matplotlib.pyplot as plt
'''
'''
x = np.arange(1, 20)
prime_numbers = []

# 소수인지 확인하는 함수
def is_prime(num):
    if num < 2:
        return False
    count = 0
    for i in range(1, num + 1):
        if num % i == 0:
            count += 1
    return count == 2

# 배열 x에서 소수들을 찾아서 저장
for i in x:
    if is_prime(i):
        prime_numbers.append(i)

# 소수들을 그래프로 시각화
plt.plot(prime_numbers, np.ones_like(prime_numbers), 'g*', markersize=8)
plt.xlabel("숫자")
plt.ylabel("소수 표시")
plt.show()
'''

'''
x = np.linspace(0,2 * np.pi, 1_000)
y1 = np.sin(x)
y2= np.cos(x)
y3 = np.tan(x)

plt.title('sin - cos - tan')
plt.plot(x, y1,'*', x, y2,'*', x, y3,'*')
plt.axis([0, 2 * np.pi, -2,2])
plt.show()
'''
'''
# 2x2 서브플롯 생성
fig, ax = plt.subplots(nrows=2, ncols=2)

# 난수 생성
x = np.random.randn(100)
y = np.random.randn(100)

# 첫 번째 서브플롯(ax[0, 0])에 x, y 산점도(scatter plot) 그리기
ax[0, 0].scatter(x, y)
ax[0, 0].set_title("Scatter Plot (0, 0)")

# 나머지 서브플롯에 다른 그래프 추가
ax[0, 1].plot(x, y, color='r')
ax[0, 1].set_title("Line Plot (0, 1)")

ax[1, 0].hist(x, bins=20, color='g')
ax[1, 0].set_title("Histogram (1, 0)")

ax[1, 1].hist(y, bins=20, color='b')
ax[1, 1].set_title("Histogram (1, 1)")

# 레이아웃 조정
plt.tight_layout()
plt.show()
'''

'''
import pandas as pd

# 인덱스를 값 개수에 맞춰 수정
se = pd.Series([1, 2, None, 4], index=['a', 'b', 'c', 'd'])

# Series 출력
print("Series:")
print(se)

# 결측치 여부를 확인하는 isna() 결과 출력
print("\n결측치 여부 (isna):")
result = se.isna()
print(result)

# 인덱스 'c'에 해당하는 값 출력 (결측치가 있는 부분)
print("\n인덱스 'c'에 해당하는 값 (se['c']):")
print(se['c'])


# 수입 정보를 담은 사전 생성 (올바르게 키-값 쌍으로 수정)
income = {'4월': 2500, '5월': 2500, '6월': 40000, '7월': 2500, '8월': 2500}

# Series로 변환
income_se = pd.Series(income)

# Series 출력
print("\n수입 Series:")
print(income_se)

# 특정 인덱스 '6월'에 해당하는 값 출력
print("\n'6월'에 해당하는 값:")
print(income_se['6월'])


import pandas as pd


# 월, 수익, 지출 시리즈 생성
month_se = pd.Series(['1월', '2월', '3월', '4월', '5월'])
income_se = pd.Series([5000, 6000, 7000, 8000, 9000])
expense_se = pd.Series([123, 456, 789, 100, 102])

# DataFrame 생성 (각 시리즈를 열로 추가)
df = pd.DataFrame({
    "월": month_se,
    "수익": income_se,
    "지출": expense_se
})

# DataFrame 출력
print(df)

print(df.iloc[0])
print(income_se.max())

df = pd.read_csv("Stocks.csv", index_col=None)
print(df)
print(df.iloc[1])
print(df.head())
print(df.columns)

column_list = df.columns.to_list()
print(column_list)

name = ""


if name in column_list :
    print("내장을 찾았습니다.")
else :
    print("내장을 찾지 못했습니다.")
'''

import pandas as pd

'''
df = pd.read_csv("https://github.com/dongupak/DataML/raw/main/csv/vehicle_prod.csv")
df.to_csv("vehicle.csv", encoding='utf-8')
'''
df = pd.read_csv("vehicle.csv", index_col=0)
print(df)
print(df.columns)

df_column_list = df.columns.to_list()
del(df_column_list[0])



