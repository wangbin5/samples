package wang.study.neural.result.perceptron;

import wang.study.neural.result.DataSet;
import wang.study.neural.result.NeuronNetConfig;

/**
 * 神经网络训练
 */
public class PerceptronTraining {
    //配置参数
    private NeuronNetConfig neuronNetConfig = new NeuronNetConfig();
    //感知机
    private Perceptron perceptron = new Perceptron();


    /**
     * 开始训练
     * @param trainingSet 训练集
     */
    public void train(DataSet trainingSet){
        //重置之前训练的结果
        perceptron.reset(neuronNetConfig,trainingSet.getCols()-1);
        int epoch =0;
        double error = Double.MAX_VALUE;
        //神经网络停止条件
        while(epoch < neuronNetConfig.getMaxEpochs()){
            boolean stop = true;
            for (int i = 0; i < trainingSet.getRows(); i++) {
                double[] input = trainingSet.getInputData(i);
                double realResult = trainingSet.getSingleOutputData(i);
                boolean shouldStop = perceptron.train(input,realResult);
                if(!shouldStop){
                    stop = false;
                }
            }
            if(stop){
                break;
            }

            epoch++;
        }
    }


    /**
     * 使用测试集检查代价函数值
     * @param checkSet  验证集
     * @return
     */
    public double check(DataSet checkSet){
        double cost = 0.0;
        for(int i=0;i<checkSet.getRows();i++){
            double[] input = checkSet.getInputData(i);
            double realResult = checkSet.getSingleOutputData(i);
            double predict = perceptron.prediction(input);
            cost+=Math.pow(predict-realResult,2);
        }
        cost = cost/checkSet.getRows();
        return cost;
    }

    /**
     * 返回推断结果
     * @param input 输入数据
     * @return
     */
    public double prediction(double[] input) {
        return this.perceptron.prediction(input);
    }

    public NeuronNetConfig getNeuronNetConfig() {
        return neuronNetConfig;
    }

    public void setNeuronNetConfig(NeuronNetConfig neuronNetConfig) {
        this.neuronNetConfig = neuronNetConfig;
    }
}
