from PIL import Image
import os
import numpy as np

data_dir = "C:\\Users\\COMPUTER\\Pictures\\Saved Pictures"
files = os.listdir(data_dir)

images = []

# 지원하는 이미지 확장자 목록
valid_extensions = ('.jpg', '.jpeg', '.png', '.gif', '.bmp')

for filename in os.listdir(data_dir):
    if filename.lower().endswith(valid_extensions):
        path = os.path.join(data_dir, filename)
        try:
            # 이미지 파일을 열고 배열로 변환
            images.append(np.array(Image.open(path)))
        except Exception as e:
            print(f"Error opening image {path}: {e}")

import tensorflow as tf

resized_images = np.array(np.zeros((len(images), 224, 224, 3)))
for i in range(len(images)):
    resized_images[i] = tf.image.resize(images[i], [224, 224])

preprocessed_images = tf.keras.applications.mobilenet_v2.preprocess_input(resized_images)

mobilenet_imagenet_model = tf.keras.applications.MobileNetV2(weights="imagenet")

y_pred = mobilenet_imagenet_model.predict(preprocessed_images)

topK = 1
y_pred_top = tf.keras.applications.mobilenet_v2.decode_predictions(y_pred, top=topK)

from matplotlib import pyplot as plt
import numpy as np

for i in range(len(images)):
    plt.imshow(images[i])
    plt.show()

    for k in range(topK):
        print(f'{y_pred_top[i][k][1]} ({round(y_pred_top[i][k][2] * 100, 1)} %)')