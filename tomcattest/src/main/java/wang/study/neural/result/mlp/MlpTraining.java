package wang.study.neural.result.mlp;

import wang.study.neural.result.DataSet;
import wang.study.neural.result.NeuronNetConfig;

/**
 * Created by Administrator on 2018/1/22.
 */
public class MlpTraining {
    private boolean last = false;

    //配置参数
    private NeuronNetConfig neuronNetConfig = new NeuronNetConfig();

    private Mlp mlp = new Mlp();

    /**
     * 开始训练
     * @param trainingSet 训练集
     */
    public void train(DataSet trainingSet){
        //重置之前训练的结果
        mlp.reset(neuronNetConfig);
        int epoch =0;
        double error = Double.MAX_VALUE;
        //神经网络停止条件
        while(epoch < neuronNetConfig.getMaxEpochs()){
            boolean stop = true;
            for (int i = 0; i < trainingSet.getRows(); i++) {
                double[] input = trainingSet.getInputData(i);
                double[] realResult = trainingSet.getOutputData(i);
                boolean shouldStop = mlp.train(input,realResult);
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
        int length = 1;
        double cost = 0.0;
        for(int i=0;i<checkSet.getRows();i++){
            double[] input = checkSet.getInputData(i);
            length = input.length;
            double[] realResult = checkSet.getOutputData(i);
            double[] predict = mlp.prediction(input);
            for(int j=0;j<realResult.length;j++){
                cost+=Math.pow(predict[j]-realResult[j],2);
            }

        }
        cost = cost/(length*checkSet.getRows());
        return cost;
    }

    /**
     * 返回推断结果
     * @param input 输入数据
     * @return
     */
    public double[] prediction(double[] input) {
        return this.mlp.prediction(input);
    }

    public NeuronNetConfig getNeuronNetConfig() {
        return neuronNetConfig;
    }

    public void setNeuronNetConfig(NeuronNetConfig neuronNetConfig) {
        this.neuronNetConfig = neuronNetConfig;
    }

    @Override
    public String toString() {
        return "MlpTraining{" +
                "last=" + last +
                ", neuronNetConfig=" + neuronNetConfig +
                ", mlp=" + mlp +
                '}';
    }
}
