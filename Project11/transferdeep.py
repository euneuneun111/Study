'''
import tensorflow_datasets as tfds
import numpy as np
import tensorflow as tf
from tensorflow.image import ResizeMethod


tfds.disable_progress_bar()

raw_train, raw_test = tfds.load('cats_vs_dogs', split = ['train[:80%]', 'train[20%:]'],
                                as_supervised= True)

FeaturesDict({
    'image': Image(shape=(None, None, 3), dtype = tf.unit8),
    'image/filename': Text(shape =(), dtype =tf.string),
    'label':ClassLabel(shape=(), dtype=tf.int64, num_classes=2)
})

def preprocess(image, label) :
    out_image = tf.image.resize(image, [96,96], method=ResizeMethod.BICUBIC)
    out_image = tf.keras.applications.mobilenet_v2.preprocess_input(out_image)
    return out_image, label

batch_size = 32
train_batch = raw_train.map(preprocess).batch(batch_size)
test_batch = raw_test.map(preprocess).batch(batch_size)

mobilenet_base = tf.keras.applications.MobileNetV2(
    input_shape = (224,224,3),
    weights = "imagenet",
    include_top = False)
)

mobilenet_base.trainable = False

mobilenet_model = tf.keras.Sequential([
    mobilenet_base,
    tf.keras.layers.GlobalAveragePooling2D(),
    tf.keras.layers.Dense(1)
])

mobilenet_model.complie(optimizer='adam',
                        loss = 'binary_crossentropy',
                        metrics = ['accuracy'])

mobilenet_model.fit(test_batch, epochs = 5)
mobilenet_model.evaluate(test_batch, verbose = 2)
'''

import tensorflow_datasets as tfds
import numpy as np
import tensorflow as tf
from tensorflow.image import ResizeMethod

tfds.disable_progress_bar()

# 데이터셋 로드
raw_train, raw_test = tfds.load('cats_vs_dogs', split=['train[:80%]', 'train[20%:]'],
                                 as_supervised=True)

# 전처리 함수 정의
def preprocess(image, label):
    out_image = tf.image.resize(image, [96, 96], method=ResizeMethod.BICUBIC)
    out_image = tf.keras.applications.mobilenet_v2.preprocess_input(out_image)
    return out_image, label

batch_size = 32
# 데이터셋 매핑 및 배치 설정
train_batch = raw_train.map(preprocess).batch(batch_size)
test_batch = raw_test.map(preprocess).batch(batch_size)

# MobileNetV2 모델 로드
mobilenet_base = tf.keras.applications.MobileNetV2(
    input_shape=(96, 96, 3),  # 모델 입력 크기를 96으로 변경
    weights="imagenet",
    include_top=False
)

mobilenet_base.trainable = False

# 새로운 모델 구성
mobilenet_model = tf.keras.Sequential([
    mobilenet_base,
    tf.keras.layers.GlobalAveragePooling2D(),
    tf.keras.layers.Dense(1, activation='sigmoid')  # Binary classification에 맞게 수정
])

# 모델 컴파일
mobilenet_model.compile(optimizer='adam',
                        loss='binary_crossentropy',
                        metrics=['accuracy'])

# 모델 학습
mobilenet_model.fit(train_batch, epochs=5)

# 모델 평가
mobilenet_model.evaluate(test_batch, verbose=2)
