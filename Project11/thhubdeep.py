import tensorflow_datasets as tfds

tfds.disable_progress_bar()

raw_train, raw_test = tfds.load(
    'cats_vs_dogs',
    split=['train[:80%]', 'train[20%:]'],
    as_supervised=True
)

import  numpy as np
import tensorflow as tf

def preprocess(image, label):
    out_image = tf.image.resize(image/255, [224,224])
    return out_image,label

batch_size = 32
train_batch = raw_train.map(preprocess).batch(batch_size)
test_batch = raw_test.map(preprocess).batch(batch_size)

import tensorflow_hub as hub

url = "https://tfhub.dev/google/imagenet/mobilenet_v2_100_224/feature_vector/4"

hub_model_transfer = tf.keras.Sequential([
    hub.KerasLayer(url, input_shape=(224,224,3), trainable=False),
    tf.keras.layers.Dense(1)
])

hub_model_transfer.compile(optimizer='adam',
                           loss='binary_crossentropy',
                           metrics=['accuracy'])

hub_model_transfer.fit(train_batch, epochs = 5)

hub_model_transfer.evaluiate(test_batch, verbose = 5)

