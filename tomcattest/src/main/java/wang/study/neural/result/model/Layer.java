package wang.study.neural.result.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 层
 */
public class Layer {
    private List<Neuron> items = new ArrayList<>();

    public List<Neuron> getItems() {
        return items;
    }

    public void setItems(List<Neuron> items) {
        this.items = items;
    }

    //运算后返回结果
    public double[] calculate(double[] input) {
        double[] result = new double[items.size()];
        for(int i=0;i<result.length;i++){
            Neuron neuron = items.get(i);
            result[i] = neuron.calculate(input);
        }
        return result;
    }
}
