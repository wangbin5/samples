import tensorflow as tf
import numpy as np

x_data = np.random.rand(100)
y_data =  x_data*0.1+0.2

b = tf.Variable(0.0)
k = tf.Variable(0.0)
y = k*x_data+b

loss = tf.reduce_mean(tf.square(y_data-y))

optimizer = tf.train.GradientDescentOptimizer(0.2)

train = optimizer.minimize(loss)

init = tf.global_variables_initializer()

with tf.Session() as sess :
    sess.run(init)
    for step in range(201):
        sess.run(train)
        if step %20 ==0 :
            #验证准确率
            correct_prediction = tf.equal(tf.argmax(y,1),tf.argmax(y_data,1))
            accuracy = tf.reduce_mean(tf.cast(correct_prediction,tf.float32))
            print(accuracy.eval())

