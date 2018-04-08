package wang.study.neural.result.mlp;

import wang.study.neural.result.NeuronNetConfig;

/**
 * Created by Administrator on 2018/1/22.
 */
public class Mlp {
    private MlpLayer hiddenLayer;

    public void reset(NeuronNetConfig neuronNetConfig) {
        hiddenLayer = this.createlayer(3,2,neuronNetConfig,null);
        MlpLayer outputLayer =this.createlayer(2,3,neuronNetConfig,hiddenLayer);

    }

    private MlpLayer createlayer(int nodeCount,int weightLength, NeuronNetConfig neuronNetConfig, MlpLayer last) {
        return new MlpLayer(nodeCount,weightLength,last, neuronNetConfig);
    }


    public boolean train(double[] input, double[] result) {
        return hiddenLayer.train(input,result);
    }

    public double[] prediction(double[] input) {
       MlpLayer layer = this.hiddenLayer;
       while(layer!=null){
            input = layer.preidct(input);
            layer = layer.getNext();
        }
        return input;
    }

    @Override
    public String toString() {
        return "Mlp{" +
                "hiddenLayer=" + hiddenLayer +
                '}';
    }
}
