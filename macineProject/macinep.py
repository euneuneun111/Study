'''
import seaborn as sns
import pandas as pd

titanic = sns.load_dataset("titanic")


titanic['age'] = round(titanic['age'], filter(titanic['age'].median))
print(titanic)

print(titanic['embarked'].value_counts(()))
titanic['embarked'] = titanic["embarked"].fillna('S')
titanic.to_csv(path_or_buf="titanic.csv", index=False)
'''
from random import random

from PIL.GimpGradientFile import linear
from pandas.core.common import random_state
from scipy.special import y_pred

'''
from sklearn import linear_model
import matplotlib.pyplot as plt

# 키와 몸무게 데이터 (단위: cm, kg)
x = [[150], [160], [170], [180], [190], [200]]  # 키 (2차원 배열로 변경)
y = [50, 60, 70, 80, 90, 100]                  # 몸무게 (1차원 배열)

# 산점도 (Scatter plot) 시각화
plt.scatter([i[0] for i in x], y, color='blue')  # 키와 몸무게를 시각화
plt.xlabel('Height (cm)')
plt.ylabel('Weight (kg)')
plt.title('Height vs. Weight')
plt.show()

# 선형 회귀 모델 학습
regression = linear_model.LinearRegression()
regression.fit(x, y)

# 키에 따른 예측된 몸무게 계산
y_hat = regression.predict(x)
print("Predicted weights:", y_hat)

# 새 키 값으로 예측하기
new_heights = [[155], [175], [195]]  # 새로운 키 데이터
predicted_weights = regression.predict(new_heights)
print("Predicted weights for new heights:", predicted_weights)
'''

'''
import matplotlib.pylab as plt
import numpy as np
from sklearn.linear_model import LinearRegression
from sklearn import datasets

diabetes_x, diabetes_y = datasets.load_diabetes(return_X_y=True)

diabetes_x_new = diabetes_x[:,np.newaxis,2]

from sklearn.model_selection import train_test_split
x_train, x_test, y_train, y_test = train_test_split(diabetes_x_new, diabetes_y,
                                                     text_size=0.1, random_state=0)

regr = linear_model.LinearRegression()
regr.fit(x_train,y_train)

y_pred = model.predict(x_test)

plt.plot(y_test, y_pred,'.')

plt.scatter(x_test,y_test, color='black')
plt.plot(x_test,y_pred,color = 'blue', linewidth = 3)
plt.show()

'''

