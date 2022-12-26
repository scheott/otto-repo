import tensorflow as tf
import pandas as pd
import matplotlib.pyplot as plt

df = pd.read_csv("CombinedData.csv")

plt.plot(df["SentenceScore"],df["High"],"ro")
plt.show()

X= df["SentenceScore"]
y = df["High"]

X_train = X[:40]
y_train = y[:40]
X_test = X[40:]
y_test = y[40:]

tf.random.set_seed(42)  #first we set random seed
model = tf.keras.Sequential([
                             tf.keras.layers.Dense(1)
])
model.compile( loss = tf.keras.losses.mae, #mae stands for mean absolute error
              optimizer = tf.keras.optimizers.SGD(), #stochastic GD
              metrics = ['mae'])
model.fit( X_train, y_train, epochs = 1)

preds = model.predict(X_test)
print(preds)

def plot_preds(traindata = X_train,
               trainlabels = y_train,
               testdata = X_test,
               testlabels = y_test,
               predictions = preds):
  plt.figure(figsize=(12,6))
  plt.scatter(traindata, trainlabels, c="b", label="Training data")
  plt.scatter(testdata, testlabels, c="g", label="Testing data")
  plt.scatter(testdata, predictions, c="r", label="Predictions")
  plt.legend()
  plt.show()

plot_preds(traindata = X_train,

           trainlabels = y_train,

           testdata = X_test,

           testlabels = y_test,

           predictions = preds)

#Yeah this model sucked

tf.random.set_seed(42)
model_1 = tf.keras.Sequential([
                               tf.keras.layers.Dense(1)
])
model_1.compile( loss = tf.keras.losses.mae,
                optimizer = tf.keras.optimizers.SGD(),
                metrics = ['mae'])
model_1.fit( X_train, y_train, epochs = 100, verbose = 0)

preds = model_1.predict(X_test)
print(preds)

def plot_preds(traindata = X_train,
               trainlabels = y_train,
               testdata = X_test,
               testlabels = y_test,
               predictions = preds):
  plt.figure(figsize=(12,6))
  plt.scatter(traindata, trainlabels, c="b", label="Training data")
  plt.scatter(testdata, testlabels, c="g", label="Testing data")
  plt.scatter(testdata, predictions, c="r", label="Predictions")
  plt.legend()
  plt.show()

plot_preds(traindata = X_train,

           trainlabels = y_train,

           testdata = X_test,

           testlabels = y_test,

           predictions = preds)

#Overall this project has very poor predictions due to lack of data, if I were to obtain the amount of data I wanted for this project,
#It would take a very long time due to the twitter scraping and NLP library
