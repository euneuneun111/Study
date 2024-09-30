import tensorflow as tf

mnist = tf.keras.datasets.mnist
(x_train, y_train), (x_test, y_test) = mnist.load_data()

x_train = x_train / 255.0
x_test = x_test / 255.0

mip_model = tf.keras.models.Sequential([
    tf.keras.layers.Flatten(input_shape=(28,28)),
    tf.keras.layers.Dense(128, activation='relu'),
    tf.keras.layers.Dense(10, activation='softmax')
])

mip_model.compile(optimizer = 'adam',
                  loss = 'sparse_categorical_crossentropy',
                  metrics=['accuracy'])

mip_model.summary()

mip_model.fit(x_train,y_train, epochs = 5)

mip_model.evaluate(x_test, y_test, verbose = 2)

inputs = tf.keras.Input(shape=(28,28))

x = tf.keras.layers.Flatten()(inputs)
x = tf.keras.layers.Dense(128, activation = 'relu')(x)
outputs = tf.keras.layers.Dense(10, activation='softmax')(x)

mip_model = tf.keras.Model(inputs = inputs, outputs = outputs)

class MLP_Model(tf.keras.Model):
    def __init__(self):
        super(MLP_Model, self).__init__()
        self.flatten = tf.keras.layers.Flatten()
        self.dense = tf.keras.layers.Dense(128, activation= 'relu')
        self.softmax = tf.keras.layers.Dense(10, activation= 'softmax')

    def call(self, inputs):
        x = self.flatten(inputs)
        x = self.dense(x)
        return self.softmax(x)

mlp_model = MLP_Model()

x_train_4d = x_train.reshape(-1, 28, 28, 1)
x_test_4d = x_test.reshape(-1, 28, 28, 1)

cnn_model = tf.keras.models.Sequential([
    tf.keras.layers.Conv2D(32, (3,3), activation='relu', input_shape = (28,28,1)),
    tf.keras.layers.MaxPooling2D((2,2)),
    tf.keras.layers.Conv2D(64,(3,3), activation='relu'),
    tf.keras.layers.MaxPooling2D((2,2)),
    tf.keras.layers.Conv2D(64,(3,3), activation='relu'),
    tf.keras.layers.Flatten(),
    tf.keras.layers.Dense(64, activation = 'relu'),
    tf.keras.layers.Dense(10, activation = 'softmax')

])

cnn_model.compile(optimizer = 'adam',
                  loss = 'sparse_categorical_crossentropy',
                  metrics=['accuracy'])
cnn_model.summary()
cnn_model.fit(x_train_4d, y_train, epochs = 5)
cnn_model.evaluate(x_train_4d, y_test, verbose = 5)



resized_x_train = tf.image.resize(x_train_4d, [32,32])
resized_x_test = tf.image.resize(x_test_4d, [32,32])

resnet_model = tf.keras.applications.ResNet50V2(
    input_shape= (32,32,1),
    classes = 10,
    weights=None)
#resnot_model.summary()

resnet_model.compile(optimizer = 'adam',
                     loss='sparse_categorical_crossentropy',
                     metrics=['accuracy'])

resnet_model.fit(resized_x_train, y_train, epochs = 5)
resnet_model.evaluate(resized_x_test, y_test, verbose = 5)
