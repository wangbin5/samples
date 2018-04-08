package wang.study.neural.result.perceptron;

import wang.study.neural.result.NeuronNetConfig;
import wang.study.neural.result.model.Neuron;

/**
 * 感知机
 */
public class Perceptron {
    /**
     * 神经元
     */
    private Neuron neuron;

    /**
     * 结果要求
     */
    private NeuronNetConfig config;



    /**
     * 训练
     * @param input 输入数据
     * @param expect 结果值
     * @return 返回是否需要停止训练
     */
    public boolean train(double[] input,double expect){
        double netValue = this.neuron.calculate(input);
        double result = this.config.getActivateFunction().activation(netValue);
        double error =  result - expect;
        if(Math.abs(error) > config.getTargetError()){
            this.learn(input,netValue,error);
            return false;
        }
        return true;
    }
    public void learn(double[] input, double netValue,double error) {
        for(int i=0;i<input.length;i++){
            double value = input[i];
            double wi = config.getGrowthRate() * error * value * this.config.getActivateFunction().derivative(netValue);
            neuron.addWeight(i,wi);
        }
    }
    /**
     * 返回推断结果
     * @param input 输入数据
     * @return
     */
    public double prediction(double[] input) {
        double netValue = this.neuron.calculate(input);
        double result = this.config.getActivateFunction().activation(netValue);
        return result;
    }

    /**
     * 重置之前训练的结果
     * @param neuronNetConfig
     */
    public void reset(NeuronNetConfig neuronNetConfig, int length) {
        this.config = neuronNetConfig;
        this.neuron = new Neuron(length);
    }
}
