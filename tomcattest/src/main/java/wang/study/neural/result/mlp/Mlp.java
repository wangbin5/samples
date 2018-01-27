package wang.study.neural.result.mlp;

import wang.study.neural.lesson1.Layer;
import wang.study.neural.result.NeuralNetConfig;
import wang.study.neural.result.activate.ActivateFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/22.
 */
public class Mlp {
    private MlpLayer first;

    public void reset(NeuralNetConfig neuralNetConfig,  int nodeCount,int layerCount) {
        MlpLayer last = null;
        for(int i=0;i<layerCount;i++){
            last = createlayer(nodeCount,neuralNetConfig,last);
            if(this.first == null){
                this.first = last;
            }

        }
    }

    private MlpLayer createlayer(int nodeCount, NeuralNetConfig neuralNetConfig, MlpLayer last) {
        return new MlpLayer(nodeCount,last,neuralNetConfig);
    }


    public boolean train(double[] input, double[] result) {
        return first.train(input,result);
    }

    public double[] prediction(double[] input) {
        MlpLayer layer = this.first;
       while(layer!=null){
            input = layer.preidct(input);
            layer = layer.getNext();
        }
        return input;
    }

    @Override
    public String toString() {
        return "Mlp{" +
                "first=" + first +
                '}';
    }
}
