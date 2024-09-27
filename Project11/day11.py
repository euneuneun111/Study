
'''
import numpy as np
import matplotlib.pyplot as plt
from sklearn.linear_model import LinearRegression

# 데이터
x = np.array([120, 130, 140, 150, 160])
y = np.array([20, 30, 40, 50, 60])

# 파라미터 초기화
w = 1.0  # 초기 가중치
b = 1.0  # 초기 절편
lr = 0.01  # 학습률
n = len(x)  # 데이터 포인트 개수
epochs = 1000  # 학습 횟수

# 경사 하강법
for _ in range(epochs):
    y_hat = w * x + b  # 예측값 계산
    error = y_hat - y  # 오차 계산

    # 가중치와 절편의 변화율 계산
    dw = (2 / n) * np.sum(x * error)
    db = (2 / n) * np.sum(error)

    # 가중치와 절편 업데이트
    w -= lr * dw
    b -= lr * db

print(f'경사 하강법으로 구한 W: {w}, B: {b}')

# sklearn을 사용한 선형 회귀
x_new = x.reshape(-1, 1)
y_new = y

# 모델 학습
regression = LinearRegression()
regression.fit(x_new, y_new)

# 학습된 가중치와 절편 출력
print(f'sklearn으로 구한 W: {regression.coef_[0]}, b: {regression.intercept_}')

# 그래프 시각화
plt.scatter(x, y, color='black', label='Data')  # 데이터 점

# 경사 하강법으로 구한 회귀선
y_pred_grad = w * x + b
plt.plot(x, y_pred_grad, color='blue', label='Gradient Descent Fit')

# sklearn으로 구한 회귀선
y_pred_sklearn = regression.predict(x_new)
plt.plot(x, y_pred_sklearn, color='red', linestyle='--', label='Sklearn LinearRegression Fit')

plt.xlabel('X (Feature)')
plt.ylabel('Y (Target)')
plt.title('Linear Regression: Gradient Descent vs Sklearn')
plt.legend()
plt.show()
'''
from array import array
from pickletools import optimize

import keras
import matplotlib.pyplot as plt
from fontTools import version
from unicodedata import digit

'''
import pandas as pd
from sklearn.datasets import load_iris
from sklearn.model_selection import train_test_split
from sklearn.neighbors import KNeighborsClassifier

# Iris 데이터셋 로드
iris = load_iris()

# 데이터와 타겟 출력
data = iris['data']  # 특성 데이터
target = iris['target']  # 타겟 데이터

# 데이터 정보 출력
print("Data:\n", data)
print("Data Shape:", data.shape)
print("Target:\n", target)
print("Target Size:", target.size)

# Pandas DataFrame 생성
iris_df = pd.DataFrame(data, columns=iris['feature_names'])
print(iris_df.head())

# 타겟 데이터를 DataFrame에 추가
iris_df['target'] = target
print("\nDataFrame with Target:\n", iris_df.head())

# 학습과 테스트 데이터셋 분리 (train_test_split)
X_train, X_test, y_train, y_test = train_test_split(data, target, test_size=0.2, random_state=42)

# KNN 분류기 모델 학습
k = 5  # K값 설정
knn = KNeighborsClassifier(n_neighbors=k)
knn.fit(X_train, y_train)

# 모델을 사용하여 테스트 데이터에 대한 예측 수행
y_predict = knn.predict(X_test)
print("Predicted Target Values:\n", y_predict)

# 실제 y_test와 비교
print("Actual Target Values:\n", y_test)

from sklearn import metrics

scores = metrics.accuracy_score(y_test, y_predict)
print(f"진척도 : {scores}")

new_flower = [[3.0, 4.0, 5.0, 7.0]]
new_flower_predict = knn.predict(new_flower)

print(new_flower_predict)
'''
'''
from sklearn import datasets
import matplotlib.pyplot as plt

# digits 데이터셋 로드
digits = datasets.load_digits()

# 데이터 확인
print("Digits Data:\n", digits['data'])
print("Digits Target:\n", digits['target'])

# 특성 이름은 제공되지 않음 (따라서 이 부분 제거)
# print(digits['feature_names'])  # digits 데이터셋에는 feature_names가 없습니다

# digits 이미지 출력
print("First Image Data:\n", digits.images[0])

# 첫 번째 이미지 시각화
plt.imshow(digits.images[2], cmap=plt.cm.gray_r, interpolation='nearest')
plt.show()


data = digits.images.reshape(1797, -1)

print('data', data)
print(data.shape)
'''
'''
from sklearn import datasets
from sklearn.model_selection import train_test_split

digits = datasets.load_digits()

(x_train, x_test, y_train, y_test) = train_test_split(digits.data, digits.target, train_size=0.1)

print(x_train)

from sklearn.neighbors import KNeighborsClassifier

k = 6
knn = KNeighborsClassifier(n_neighbors=k)

knn.fit(x_train, y_train)
predictions = knn.predict(x_test)

print(predictions)
print((y_test))

from  sklearn.metrics import classification_report
print("evaluation on testing data")
print(classification_report(y_test,predictions))

y_predict = knn.predict([x_test[10]],)

plt.imshow(x_test[10].reshape(8,8), cmap=plt.cm.gray_r, interpolation='nearest')
plt.show()

print(y_predict)
print(y_test[10])
'''
'''
from sklearn.linear_model import Perceptron

x = [[0,0],
     [0,1],
     [1,0],
     [1,1]]
y = [0,0,0,1]

percept = Perceptron(tol = 0.001)
percept.fit(x,y)

y_predict = percept.predict(x)
print(y_predict)

y_prediction = percept.predict([[1,1]])
print(y_predict)
print(y_prediction)
'''
'''
from sklearn.linear_model import Perceptron

x = [[0,0],
     [0,1],
     [1,0],
     [1,1]]
y = [0,1,1,0]

percept = Perceptron(tol = 0.001)
percept.fit(x,y)

y_predict = percept.predict(x)
print(y_predict)

y_prediction = percept.predict([[0,1]])
print(y_predict)
print(y_prediction)
'''

import keras
import tensorflow as tf

x = tf.constant([[0,0],[0,1],[1,0],[1,1]])
y = tf.constant([0,1,1,0])

model = keras.models.Sequential(name="xor")
input = keras.Input(shape=(2,))

model.add(input)
layer1 = keras.layers.Dense(units=1, activation='sigmoid',name = 'layer1')
model.add(layer1)

layer2 = keras.layers.Dense(units=2, activation='sigmoid',name = 'layer2' )
output = keras.layers.Dense(units=1, activation='sigmoid',name = 'output')

model.add(output)
print(model.summary())

model.compile(loss= "mse", optimizer= keras.optimizers.SGD(learning_rate=0.5))
model.fit(x,y, epochs = 5000, verbose = 2)
print(model.predict(x))
model.save("xorrate keras")
