#读取数据集
from tensorflow.examples.tutorials.mnist import input_data
mnist = input_data.read_data_sets("MNINST_data/",one_hot=True)

#创建交互session
import tensorflow as tf
sess = tf.InteractiveSession()

#定义占位符x
x = tf.placeholder(tf.float32,[None,784])

#创建权值向量和常数项权值
w = tf.Variable(tf.zeros([784,10]))
b = tf.Variable(tf.zeros([10]))

#定义激活函数
y = tf.nn.softmax(tf.matmul(x,w)+b)

#定义占位符y_
y_ = tf.placeholder(tf.float32,[None,10])

#定义损失函数
cross_entropy= tf.reduce_mean(-tf.reduce_sum(y_ * tf.log(y),reduction_indices=[1]))

#定义训练优化器
train_step = tf.train.GradientDescentOptimizer(0.5).minimize(cross_entropy)

#初始化全局变量
tf.global_variables_initializer().run()

#循环批量填充数据
for i in range(1000):
    batch_xs, batch_ys = mnist.train.next_batch(100)
    train_step.run({x:batch_xs,y_:batch_ys})

#验证准确率
correct_prediction = tf.equal(tf.argmax(y,1),tf.argmax(y_,1))
accuracy = tf.reduce_mean(tf.cast(correct_prediction,tf.float32))
print(accuracy.eval({x:mnist.test.images,y_: mnist.test.labels}))