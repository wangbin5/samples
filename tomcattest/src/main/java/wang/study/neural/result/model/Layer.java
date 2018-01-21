package wang.study.neural.result.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 层
 */
public class Layer {
    private List<Neural> items = new ArrayList<>();

    public List<Neural> getItems() {
        return items;
    }

    public void setItems(List<Neural> items) {
        this.items = items;
    }

    //运算后返回结果
    public double[] calculate(double[] input) {
        double[] result = new double[items.size()];
        for(int i=0;i<result.length;i++){
            Neural neural = items.get(i);
            result[i] = neural.calculate(input);
        }
        return result;
    }
}
