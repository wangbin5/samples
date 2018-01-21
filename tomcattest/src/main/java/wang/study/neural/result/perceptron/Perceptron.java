package wang.study.neural.result.perceptron;

import wang.study.neural.result.NeuralNetConfig;
import wang.study.neural.result.activate.ActivateFunction;
import wang.study.neural.result.model.Neural;

/**
 * 感知机
 */
public class Perceptron {
    /**
     * 神经元
     */
    private Neural neural;

    /**
     * 结果要求
     */
    private NeuralNetConfig neuralNetConfig;
    /**
     * 激活函数
     */
    private ActivateFunction activateFunction;


    /**
     * 训练
     * @param input 输入数据
     * @param expect 结果值
     * @return 返回是否需要停止训练
     */
    public boolean train(double[] input,double expect){
        double netValue = this.neural.calculate(input);
        double result = this.activateFunction.activation(netValue);
        double error = result - expect;
        if(Math.abs(error) > neuralNetConfig.getMaxEpochs()){
            this.learn(input,netValue,error);
            return false;
        }
        return true;
    }
    public void learn(double[] input, double netValue,double error) {
        for(int i=0;i<input.length;i++){
            double wi = neuralNetConfig.getGrowthRate() * error * input[i] * this.activateFunction.derivative(netValue);
            neural.addWeight(i,wi);
        }
    }
    /**
     * 返回推断结果
     * @param input 输入数据
     * @return
     */
    public double prediction(double[] input) {
        double netValue = this.neural.calculate(input);
        double result = this.activateFunction.activation(netValue);
        return result;
    }

    /**
     * 重置之前训练的结果
     * @param neuralNetConfig
     */
    public void reset(NeuralNetConfig neuralNetConfig,int length) {
        this.neuralNetConfig = neuralNetConfig;
        this.neural = new Neural(length);
    }
}
