package wang.study.neural.result.model;

import wang.study.neural.result.DataSet;
import wang.study.neural.result.activate.ActivateFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * 神经元网络
 */
public class NeuralNet {
    private Layer input;
    private Layer output;
    private List<Layer> hiddenLayers = new ArrayList<>();
    private ActivateFunction activateFunction;


    //训练,返回netvalue
    public double train(double[] input) {
        double error = 0.0;

        for (Layer layer : this.hiddenLayers) {
            input = layer.calculate(input);
        }

        double netValue  = output.calculate(input)[0];
        return netValue;
    }

    public double convertToResult(double netValue){
        return this.activateFunction.activation(netValue);
    }



    //验证并打印结果
    public void checkAndPrint(DataSet checkSet) {
    }
    //预测结果
    public double prediction(double[] doubles) {
        return 0.0;
    }

    //更新权重
    public void teach(double[] input, double netValue,double error) {
    }
}
